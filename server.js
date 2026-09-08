const http = require("http");
const fs = require("fs");
const path = require("path");

// The container runs Nginx on 8080 and proxies traffic to localhost:3000
const PORT = 3000;
const HOST = "0.0.0.0";
const PUBLIC_DIR = path.join(__dirname, "public");

// Locate the compiled APK file
function findApkFile() {
  const candidatePaths = [
    path.join(__dirname, "app/build/outputs/apk/debug/app-debug.apk"),
    path.join(__dirname, "app-debug.apk"),
    path.join(__dirname, "artha-mantralaya.apk")
  ];
  for (const p of candidatePaths) {
    if (fs.existsSync(p)) {
      return p;
    }
  }
  return null;
}

const MIME_TYPES = {
  ".html": "text/html; charset=utf-8",
  ".css": "text/css; charset=utf-8",
  ".js": "application/javascript; charset=utf-8",
  ".json": "application/json; charset=utf-8",
  ".webmanifest": "application/manifest+json; charset=utf-8",
  ".svg": "image/svg+xml",
  ".png": "image/png",
  ".jpg": "image/jpeg",
  ".jpeg": "image/jpeg",
  ".webp": "image/webp",
  ".ico": "image/x-icon",
  ".apk": "application/vnd.android.package-archive"
};

const server = http.createServer((req, res) => {
  const parsedUrl = new URL(req.url, `http://${req.headers.host || "localhost:3000"}`);
  let pathname = decodeURIComponent(parsedUrl.pathname);

  // 1. APK Download Endpoint
  if (pathname === "/download/app" || pathname === "/download/artha-mantralaya.apk" || pathname === "/download/app-debug.apk") {
    const apkPath = findApkFile();
    if (!apkPath) {
      res.writeHead(404, { "Content-Type": "text/plain; charset=utf-8" });
      res.end("Android APK not found on server. Please trigger a build first.");
      return;
    }

    try {
      const stat = fs.statSync(apkPath);
      res.writeHead(200, {
        "Content-Type": "application/vnd.android.package-archive",
        "Content-Disposition": 'attachment; filename="artha-mantralaya.apk"',
        "Content-Length": stat.size,
        "Cache-Control": "no-cache"
      });

      const readStream = fs.createReadStream(apkPath);
      readStream.pipe(res);
      return;
    } catch (err) {
      console.error("Error streaming APK file:", err);
      res.writeHead(500, { "Content-Type": "text/plain" });
      res.end("Server error while reading APK file.");
      return;
    }
  }

  // 2. Application Info Endpoint
  if (pathname === "/api/app-info") {
    const apkPath = findApkFile();
    let apkSizeMb = 0;
    let apkAvailable = false;
    if (apkPath) {
      try {
        const stat = fs.statSync(apkPath);
        apkSizeMb = parseFloat((stat.size / (1024 * 1024)).toFixed(2));
        apkAvailable = true;
      } catch (_) {}
    }

    res.writeHead(200, { "Content-Type": "application/json; charset=utf-8" });
    res.end(JSON.stringify({
      appName: "Artha Mantralaya",
      nepaliName: "अर्थ मन्त्रालय",
      version: "1.0.0",
      apkAvailable,
      apkSizeMb,
      downloadUrl: "/download/artha-mantralaya.apk",
      environment: "production-ready"
    }));
    return;
  }

  // 3. Static Files Serving
  if (pathname === "/") {
    pathname = "/index.html";
  }

  let filePath = path.join(PUBLIC_DIR, pathname);

  // Security: prevent directory traversal outside public
  if (!filePath.startsWith(PUBLIC_DIR)) {
    res.writeHead(403, { "Content-Type": "text/plain" });
    res.end("Forbidden");
    return;
  }

  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      // SPA Fallback: serve index.html for extensionless paths
      if (!path.extname(pathname)) {
        const indexPath = path.join(PUBLIC_DIR, "index.html");
        fs.readFile(indexPath, (err2, data) => {
          if (err2) {
            res.writeHead(404, { "Content-Type": "text/plain" });
            res.end("Not Found");
          } else {
            res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
            res.end(data);
          }
        });
        return;
      }
      res.writeHead(404, { "Content-Type": "text/plain" });
      res.end("Not Found");
      return;
    }

    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || "application/octet-stream";

    res.writeHead(200, {
      "Content-Type": contentType,
      "Content-Length": stats.size,
      "Cache-Control": ext === ".html" ? "no-cache" : "public, max-age=3600"
    });

    const stream = fs.createReadStream(filePath);
    stream.pipe(res);
  });
});

server.listen(PORT, HOST, () => {
  console.log(`[Artha Mantralaya Web Server] Listening at http://${HOST}:${PORT}`);
  console.log(`[APK Status] ${findApkFile() ? "APK available for download" : "APK not yet found"}`);
});
