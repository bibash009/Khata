/**
 * ARTHA MANTRALAYA - PRODUCTION WEB APPLICATION & OFFLINE ENGINE
 * 100% Functionality-Preserved Single-Page Application
 */

(function () {
  "use strict";

  // =========================================================================
  // 1. LOCALIZATION (NEPALI & ENGLISH)
  // =========================================================================
  const L10N = {
    ne: {
      appName: "अर्थ मन्त्रालय",
      appSubtitle: "Artha Mantralaya",
      tabHome: "गृहपृष्ठ",
      tabPersonal: "व्यक्तिगत",
      tabGroup: "समूह",
      tabReports: "रिपोर्ट",
      downloadApp: "Download App",
      welcome: "नमस्ते",
      monthlyBudget: "मासिक बजेट",
      spent: "खर्च भएको",
      remaining: "बाँकी रकम",
      editBudget: "बजेट सेट",
      addExpense: "+ व्यक्तिगत खर्च",
      addGroupExpense: "+ समूह खर्च",
      groupOverview: "समूह हिसाब सारांश",
      viewDetails: "विस्तृत हेर्नुहोस् →",
      recentTransactions: "भर्खरका कारोबारहरू",
      viewAll: "सबै हेर्नुहोस् →",
      noRecent: "हालसम्म कुनै कारोबार छैन।",
      noGroupSummary: "कुनै समूह कारोबार छैन।",
      overbudgetNotice: "सावधान! तपाईंको खर्च यस महिनाको बजेट सीमाभन्दा बढी भएको छ।",
      historyCount: "कारोबारहरू",
      total: "जम्मा",
      generateBill: "रसिद",
      edit: "सम्पादन",
      delete: "मेटाउनुहोस्",
      subtabSummary: "सारांश",
      subtabMembers: "सदस्यहरू",
      subtabTransactions: "लेनदेन",
      totalGroupExpenses: "कुल समूह खर्च",
      totalMembers: "कुल सदस्य संख्या",
      memberBalances: "सदस्यहरूको खुद हिसाब",
      settlementPlan: "ऋण फरफारक योजना",
      settlementDesc: "न्यूनतम लेनदेनमा हिसाब मिलाउन स्वचालित गणना गरिएको फरफारक योजना:",
      markSettled: "चुक्ता भयो",
      addMember: "सदस्य थप्नुहोस्",
      addTx: "नयाँ लेनदेन",
      reportsTitle: "वित्तीय विश्लेषण र प्रतिवेदन",
      reportsSubtitle: "मासिक खर्चको विधागत विश्लेषण र प्रतिवेदन डाउनलोड गर्नुहोस्।",
      categoryBreakdown: "विधागत खर्च विभाजन",
      downloadReports: "प्रतिवेदन डाउनलोडहरू",
      monthlyPersonalBill: "व्यक्तिगत मासिक खर्च रसिद",
      monthlyPersonalBillDesc: "छानिएको महिनाको सम्पूर्ण व्यक्तिगत खर्चको आधिकारिक रसिद (PDF)",
      customGroupReport: "कस्टम समूह हिसाब रिपोर्ट",
      customGroupReportDesc: "समूह हिसाब, कसले कति तिर्यो र फरफारक तालिका (PDF, Excel, CSV)",
      generatePdf: "PDF हेर्नुहोस् / छाप्नुहोस्",
      configureExport: "रिपोर्ट कन्फिगर र डाउनलोड",
      cancel: "रद्द गर्नुहोस्",
      save: "बचत गर्नुहोस्",
      deleteConfirmTitle: "रेकर्ड हटाउने पुष्टि",
      deletePasswordPrompt: "दुर्घटनाबस डेटा हराउनबाट बच्न कृपया सुरक्षा पासवर्ड <strong>100</strong> प्रविष्ट गर्नुहोस्:",
      paidBy: "कसले तिर्यो?",
      splitWith: "कसकसमा बाँड्ने?",
      perPerson: "प्रति व्यक्ति भाग",
      allSelected: "सबै छान्नुहोस्",
      deselectAll: "हटाउनुहोस्",
      billReceipt: "आधिकारिक रसिद (Official Bill Receipt)",
      settingsTitle: "एप सेटिङहरू",
      preferences: "प्राथमिकताहरू",
      language: "भाषा",
      theme: "थिम",
      calendarMode: "पात्रो प्रणाली",
      securityPinTitle: "सुरक्षा PIN",
      securityPin: "४-अंकको सुरक्षा PIN",
      backupRestore: "ब्याकअप र पुनःस्थापना",
      backupDesc: "तपाईंको सम्पूर्ण डाटा JSON फाइलको रूपमा सुरक्षित गर्नुहोस् वा पूर्ववत फाइलबाट लोड गर्नुहोस्। यो ब्याकअप एन्ड्रोइड एपसँग पूर्ण मिल्छ।",
      exportBackup: "JSON ब्याकअप डाउनलोड",
      restoreBackup: "ब्याकअप लोड गर्नुहोस्",
      dangerZone: "खतरा क्षेत्र",
      resetAppData: "सबै डाटा खाली गर्नुहोस्",
      reset: "रिसेट",
      catFood: "खाना",
      catTransport: "यातायात",
      catEducation: "शिक्षा",
      catRent: "घरभाडा",
      catShopping: "किनमेल",
      catEntertainment: "मनोरञ्जन",
      catHealth: "स्वास्थ्य",
      catUtilities: "उपयोगिता",
      catOthers: "अन्य"
    },
    en: {
      appName: "Artha Mantralaya",
      appSubtitle: "Offline Expense Manager",
      tabHome: "Home",
      tabPersonal: "Personal",
      tabGroup: "Group",
      tabReports: "Reports",
      downloadApp: "Download App",
      welcome: "Welcome",
      monthlyBudget: "Monthly Budget",
      spent: "Total Spent",
      remaining: "Remaining",
      editBudget: "Set Budget",
      addExpense: "+ Personal Expense",
      addGroupExpense: "+ Group Expense",
      groupOverview: "Group Overview",
      viewDetails: "View Details →",
      recentTransactions: "Recent Activity",
      viewAll: "View All →",
      noRecent: "No transactions recorded yet.",
      noGroupSummary: "No group transactions recorded yet.",
      overbudgetNotice: "Warning! Your monthly spending has exceeded the budget limit.",
      historyCount: "Transactions",
      total: "Total",
      generateBill: "Bill",
      edit: "Edit",
      delete: "Delete",
      subtabSummary: "Summary",
      subtabMembers: "Members",
      subtabTransactions: "Transactions",
      totalGroupExpenses: "Total Group Expenses",
      totalMembers: "Total Members",
      memberBalances: "Member Net Balances",
      settlementPlan: "Smart Settlement Plan",
      settlementDesc: "Optimized minimum cash-flow settlement plan to clear all debts:",
      markSettled: "Mark Settled",
      addMember: "Add Member",
      addTx: "New Transaction",
      reportsTitle: "Financial Analytics & Reports",
      reportsSubtitle: "Detailed category-wise analytics breakdown and bill generation.",
      categoryBreakdown: "Category Spending Breakdown",
      downloadReports: "Report Downloads",
      monthlyPersonalBill: "Monthly Personal Bill",
      monthlyPersonalBillDesc: "Itemized official bill statement for the selected month (PDF)",
      customGroupReport: "Custom Group Report",
      customGroupReportDesc: "Group spending, individual shares, and settlement matrix (PDF, Excel, CSV)",
      generatePdf: "View / Print PDF",
      configureExport: "Configure & Download",
      cancel: "Cancel",
      save: "Save",
      deleteConfirmTitle: "Confirm Secure Deletion",
      deletePasswordPrompt: "To prevent accidental deletion, please enter security password <strong>100</strong> to confirm:",
      paidBy: "Paid By",
      splitWith: "Split With Whom?",
      perPerson: "Per Person Share",
      allSelected: "Select All",
      deselectAll: "Deselect All",
      billReceipt: "Official Bill Receipt",
      settingsTitle: "App Settings",
      preferences: "Preferences",
      language: "Language",
      theme: "Theme",
      calendarMode: "Calendar System",
      securityPinTitle: "Security PIN",
      securityPin: "4-Digit Security PIN",
      backupRestore: "Backup & Restore",
      backupDesc: "Export your entire database as a JSON backup or restore from previous backup. 100% compatible with Android app.",
      exportBackup: "Download JSON Backup",
      restoreBackup: "Restore JSON Backup",
      dangerZone: "Danger Zone",
      resetAppData: "Reset All App Data",
      reset: "Reset",
      catFood: "Food",
      catTransport: "Transport",
      catEducation: "Education",
      catRent: "Rent",
      catShopping: "Shopping",
      catEntertainment: "Entertainment",
      catHealth: "Health",
      catUtilities: "Utilities",
      catOthers: "Others"
    }
  };

  const CATEGORY_ICONS = {
    Food: "🍲",
    Transport: "🚌",
    Education: "📚",
    Rent: "🏠",
    Shopping: "🛍️",
    Entertainment: "🎬",
    Health: "💊",
    Utilities: "💡",
    Others: "🏷️"
  };

  const NEPALI_MONTHS = [
    "वैशाख (Baisakh)", "जेठ (Jestha)", "असार (Ashadh)", "साउन (Shrawan)",
    "भदौ (Bhadra)", "असोज (Ashwin)", "कात्तिक (Kartik)", "मंसिर (Mangsir)",
    "पुस (Poush)", "माघ (Magh)", "फागुन (Falgun)", "चैत (Chaitra)"
  ];

  // =========================================================================
  // 2. DATA PERSISTENCE & INITIAL STATE
  // =========================================================================
  const STORAGE_KEY = "artha_mantralaya_db";

  const DEFAULT_DB = {
    profile: {
      id: 1,
      name: "Bibash",
      photoUri: null,
      selectedLanguage: "ne",
      isDarkMode: false,
      calendarMode: "bs",
      securityPin: ""
    },
    budgets: {
      "2026-09": 35000,
      "2026-08": 30000
    },
    personalExpenses: [
      {
        id: 1,
        title: "सुपरमार्केट किनमेल (Grocery)",
        description: "मासिक दाल, चामल र तरकारी",
        amount: 4250,
        category: "Shopping",
        date: "2026-09-02",
        time: "11:30",
        notes: "भाटभटेनी सुपरमार्केट"
      },
      {
        id: 2,
        title: "अफिस यातायात (Fuel)",
        description: "पेट्रोल खर्च",
        amount: 1500,
        category: "Transport",
        date: "2026-09-04",
        time: "09:15",
        notes: ""
      },
      {
        id: 3,
        title: "खाना र खाजा (Lunch)",
        description: "टोलीसँग खाजा",
        amount: 850,
        category: "Food",
        date: "2026-09-06",
        time: "13:45",
        notes: "काठमाडौँ क्याफे"
      },
      {
        id: 4,
        title: "घरभाडा (Room Rent)",
        description: "भाद्र महिनाको भाडा",
        amount: 18000,
        category: "Rent",
        date: "2026-08-30",
        time: "10:00",
        notes: "घरबेटीलाई बैंक ट्रान्सफर"
      }
    ],
    groupMembers: [
      { id: 1, name: "Bibash Lamichhane", phone: "9800000001", notes: "Self", avatarColor: "#1976D2" },
      { id: 2, name: "Aayush Sharma", phone: "9800000002", notes: "Roommate", avatarColor: "#388E3C" },
      { id: 3, name: "Suman Thapa", phone: "9800000003", notes: "Friend", avatarColor: "#D97706" },
      { id: 4, name: "Pooja Shrestha", phone: "9800000004", notes: "Colleague", avatarColor: "#7C3AED" }
    ],
    groupTransactions: [
      {
        id: 1,
        name: "कोठाको वाइफाइ र बिजुली (Utilities)",
        amount: 2400,
        date: "2026-09-01",
        time: "16:20",
        paidById: 1,
        splitWithIds: "1,2,3,4",
        notes: "सबै सदस्यलाई बराबर रु ६००"
      },
      {
        id: 2,
        name: "शनिबारको सामूहिक डिनर (Dinner Outing)",
        amount: 3600,
        date: "2026-09-05",
        time: "20:00",
        paidById: 2,
        splitWithIds: "1,2,3",
        notes: "बिबास, आयुष र सुमन"
      }
    ]
  };

  let db = loadDatabase();

  function loadDatabase() {
    try {
      const stored = localStorage.getItem(STORAGE_KEY);
      if (stored) {
        const parsed = JSON.parse(stored);
        if (parsed && parsed.profile) {
          return parsed;
        }
      }
    } catch (e) {
      console.warn("Could not load from localStorage, using default DB:", e);
    }
    saveDatabase(DEFAULT_DB);
    return DEFAULT_DB;
  }

  function saveDatabase(data) {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
    } catch (e) {
      console.error("Failed to save to localStorage:", e);
    }
  }

  // =========================================================================
  // 3. APPLICATION STATE
  // =========================================================================
  const state = {
    activeTab: "home",
    activeGroupSubTab: "summary",
    selectedMonth: getCurrentMonthKey(),
    personalSearchQuery: "",
    personalCategoryFilter: "all",
    personalSortOrder: "date_desc",
    groupSearchQuery: "",
    pinBuffer: "",
    isLocked: Boolean(db.profile.securityPin),
    deletePendingCallback: null
  };

  function getCurrentMonthKey() {
    const d = new Date();
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    return `${yyyy}-${mm}`;
  }

  function getActiveLang() {
    return db.profile.selectedLanguage || "ne";
  }

  function t(key) {
    const lang = getActiveLang();
    return (L10N[lang] && L10N[lang][key]) || (L10N.ne[key]) || key;
  }

  function formatNpr(amount) {
    const num = Number(amount) || 0;
    return "रु " + num.toLocaleString("en-IN", { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  }

  // Approximate BS Conversion for display
  function formatDisplayDate(dateStr) {
    if (!dateStr) return "";
    if (db.profile.calendarMode === "bs") {
      const parts = dateStr.split("-");
      if (parts.length === 3) {
        const y = parseInt(parts[0], 10) + 57; // BS year offset
        const m = parseInt(parts[1], 10);
        const d = parts[2];
        const mNames = ["बैशाख", "जेठ", "असार", "साउन", "भदौ", "असोज", "कात्तिक", "मंसिर", "पुस", "माघ", "फागुन", "चैत"];
        const bsMonth = mNames[(m - 1) % 12] || parts[1];
        return `${d} ${bsMonth} ${y} (वि.सं.)`;
      }
    }
    return dateStr;
  }

  // =========================================================================
  // 4. SETTLEMENT CALCULATOR (Exact Greedy Algorithm)
  // =========================================================================
  const SettlementEngine = {
    calculateBalances(members, transactions) {
      const balanceMap = {};
      members.forEach((m) => { balanceMap[m.id] = 0.0; });

      for (const tx of transactions) {
        const splitIds = (tx.splitWithIds || "")
          .split(",")
          .filter(Boolean)
          .map((id) => parseInt(id, 10))
          .filter((id) => members.some((m) => m.id === id));

        if (splitIds.length === 0) continue;
        const share = tx.amount / splitIds.length;

        if (balanceMap.hasOwnProperty(tx.paidById)) {
          balanceMap[tx.paidById] += tx.amount;
        }

        for (const sid of splitIds) {
          if (balanceMap.hasOwnProperty(sid)) {
            balanceMap[sid] -= share;
          }
        }
      }
      return balanceMap;
    },

    calculateSettlements(members, transactions) {
      const balanceMap = this.calculateBalances(members, transactions);
      const debtors = [];
      const creditors = [];

      for (const [idStr, bal] of Object.entries(balanceMap)) {
        const id = parseInt(idStr, 10);
        if (bal < -0.01) {
          debtors.push({ id, amount: -bal });
        } else if (bal > 0.01) {
          creditors.push({ id, amount: bal });
        }
      }

      debtors.sort((a, b) => b.amount - a.amount);
      creditors.sort((a, b) => b.amount - a.amount);

      const transfers = [];
      let dIndex = 0;
      let cIndex = 0;

      while (dIndex < debtors.length && cIndex < creditors.length) {
        const debtor = debtors[dIndex];
        const creditor = creditors[cIndex];

        const minTransfer = Math.min(debtor.amount, creditor.amount);

        if (minTransfer > 0.01) {
          const debtorMember = members.find((m) => m.id === debtor.id);
          const creditorMember = members.find((m) => m.id === creditor.id);

          transfers.push({
            debtorId: debtor.id,
            debtorName: debtorMember ? debtorMember.name : `Member ${debtor.id}`,
            creditorId: creditor.id,
            creditorName: creditorMember ? creditorMember.name : `Member ${creditor.id}`,
            amount: minTransfer
          });
        }

        debtor.amount -= minTransfer;
        creditor.amount -= minTransfer;

        if (debtor.amount < 0.01) dIndex++;
        if (creditor.amount < 0.01) cIndex++;
      }

      return transfers;
    }
  };

  // =========================================================================
  // 5. DOM ELEMENTS & INITIALIZATION
  // =========================================================================
  const DOM = {
    body: document.body,
    langDisplay: document.getElementById("langDisplay"),
    themeDisplay: document.getElementById("themeDisplay"),
    downloadAppBtn: document.getElementById("downloadAppBtn"),
    langToggleBtn: document.getElementById("langToggleBtn"),
    themeToggleBtn: document.getElementById("themeToggleBtn"),
    settingsBtn: document.getElementById("settingsBtn"),
    brandBtn: document.getElementById("brandBtn"),

    // Views
    viewHome: document.getElementById("view-home"),
    viewPersonal: document.getElementById("view-personal"),
    viewGroup: document.getElementById("view-group"),
    viewReports: document.getElementById("view-reports"),

    // Nav
    desktopTabs: document.querySelectorAll(".desktop-nav .nav-tab"),
    mobileTabs: document.querySelectorAll(".mobile-bottom-bar .bnav-btn"),

    // Home
    homeUserAvatar: document.getElementById("homeUserAvatar"),
    homeUserName: document.getElementById("homeUserName"),
    activeMonthSelect: document.getElementById("activeMonthSelect"),
    budgetLimitDisplay: document.getElementById("budgetLimitDisplay"),
    budgetSpentDisplay: document.getElementById("budgetSpentDisplay"),
    budgetRemainingDisplay: document.getElementById("budgetRemainingDisplay"),
    budgetProgressFill: document.getElementById("budgetProgressFill"),
    budgetPercentage: document.getElementById("budgetPercentage"),
    budgetStatusText: document.getElementById("budgetStatusText"),
    overbudgetWarning: document.getElementById("overbudgetWarning"),
    editBudgetBtn: document.getElementById("editBudgetBtn"),
    quickAddPersonalBtn: document.getElementById("quickAddPersonalBtn"),
    quickAddGroupBtn: document.getElementById("quickAddGroupBtn"),
    gotoGroupBtn: document.getElementById("gotoGroupBtn"),
    gotoPersonalBtn: document.getElementById("gotoPersonalBtn"),
    homeGroupSummaryContent: document.getElementById("homeGroupSummaryContent"),
    homeRecentList: document.getElementById("homeRecentList"),

    // Personal
    personalSearchInput: document.getElementById("personalSearchInput"),
    clearPersonalSearchBtn: document.getElementById("clearPersonalSearchBtn"),
    personalSortSelect: document.getElementById("personalSortSelect"),
    categoryChips: document.querySelectorAll("#categoryChips .chip"),
    personalHistoryCount: document.getElementById("personalHistoryCount"),
    personalHistoryTotal: document.getElementById("personalHistoryTotal"),
    personalExpenseList: document.getElementById("personalExpenseList"),
    fabAddPersonalBtn: document.getElementById("fabAddPersonalBtn"),

    // Group
    subtabs: document.querySelectorAll(".subtabs-nav .subtab-btn"),
    groupSubviewSummary: document.getElementById("group-subview-summary"),
    groupSubviewMembers: document.getElementById("group-subview-members"),
    groupSubviewTransactions: document.getElementById("group-subview-transactions"),
    groupTotalSpent: document.getElementById("groupTotalSpent"),
    groupMemberCount: document.getElementById("groupMemberCount"),
    groupBalancesList: document.getElementById("groupBalancesList"),
    groupSettlementsList: document.getElementById("groupSettlementsList"),
    groupMembersList: document.getElementById("groupMembersList"),
    groupTransactionsList: document.getElementById("groupTransactionsList"),
    groupSearchInput: document.getElementById("groupSearchInput"),
    openAddMemberBtn: document.getElementById("openAddMemberBtn"),
    openAddGroupTxBtn: document.getElementById("openAddGroupTxBtn"),

    // Reports
    reportMonthSelect: document.getElementById("reportMonthSelect"),
    reportMonthTotal: document.getElementById("reportMonthTotal"),
    categoryAnalyticsBars: document.getElementById("categoryAnalyticsBars"),
    downloadPersonalBillBtn: document.getElementById("downloadPersonalBillBtn"),
    openCustomReportDialogBtn: document.getElementById("openCustomReportDialogBtn"),

    // Modals
    personalExpenseModal: document.getElementById("personalExpenseModal"),
    personalExpenseForm: document.getElementById("personalExpenseForm"),
    peId: document.getElementById("peId"),
    peTitle: document.getElementById("peTitle"),
    peAmount: document.getElementById("peAmount"),
    peCategory: document.getElementById("peCategory"),
    peDate: document.getElementById("peDate"),
    peTime: document.getElementById("peTime"),
    peDescription: document.getElementById("peDescription"),
    peNotes: document.getElementById("peNotes"),

    groupTxModal: document.getElementById("groupTxModal"),
    groupTxForm: document.getElementById("groupTxForm"),
    gtId: document.getElementById("gtId"),
    gtName: document.getElementById("gtName"),
    gtAmount: document.getElementById("gtAmount"),
    gtPaidBy: document.getElementById("gtPaidBy"),
    gtDate: document.getElementById("gtDate"),
    gtTime: document.getElementById("gtTime"),
    splitMembersCheckboxes: document.getElementById("splitMembersCheckboxes"),
    splitCalcPreview: document.getElementById("splitCalcPreview"),
    gtNotes: document.getElementById("gtNotes"),
    selectAllMembersBtn: document.getElementById("selectAllMembersBtn"),
    deselectAllMembersBtn: document.getElementById("deselectAllMembersBtn"),

    groupMemberModal: document.getElementById("groupMemberModal"),
    groupMemberForm: document.getElementById("groupMemberForm"),
    gmId: document.getElementById("gmId"),
    gmName: document.getElementById("gmName"),
    gmPhone: document.getElementById("gmPhone"),
    gmNotes: document.getElementById("gmNotes"),

    budgetModal: document.getElementById("budgetModal"),
    budgetForm: document.getElementById("budgetForm"),
    budgetMonthInput: document.getElementById("budgetMonthInput"),
    budgetAmountInput: document.getElementById("budgetAmountInput"),

    profileModal: document.getElementById("profileModal"),
    profileForm: document.getElementById("profileForm"),
    profileNameInput: document.getElementById("profileNameInput"),

    secureDeleteModal: document.getElementById("secureDeleteModal"),
    secureDeletePasswordInput: document.getElementById("secureDeletePasswordInput"),
    confirmSecureDeleteBtn: document.getElementById("confirmSecureDeleteBtn"),

    billViewerModal: document.getElementById("billViewerModal"),
    billPaperContent: document.getElementById("billPaperContent"),
    printBillBtn: document.getElementById("printBillBtn"),

    customReportModal: document.getElementById("customReportModal"),
    customReportForm: document.getElementById("customReportForm"),
    crFilterType: document.getElementById("crFilterType"),
    crMonthRow: document.getElementById("crMonthRow"),
    crYearRow: document.getElementById("crYearRow"),
    crCustomDateRow: document.getElementById("crCustomDateRow"),
    crMonthKey: document.getElementById("crMonthKey"),
    crYearKey: document.getElementById("crYearKey"),
    crStartDate: document.getElementById("crStartDate"),
    crEndDate: document.getElementById("crEndDate"),

    settingsModal: document.getElementById("settingsModal"),
    settingLanguageSelect: document.getElementById("settingLanguageSelect"),
    settingThemeSelect: document.getElementById("settingThemeSelect"),
    settingCalendarSelect: document.getElementById("settingCalendarSelect"),
    setupPinBtn: document.getElementById("setupPinBtn"),
    pinStatusDesc: document.getElementById("pinStatusDesc"),
    exportBackupBtn: document.getElementById("exportBackupBtn"),
    restoreFileInput: document.getElementById("restoreFileInput"),
    resetAppDataBtn: document.getElementById("resetAppDataBtn"),

    // PIN Screen
    pinLockScreen: document.getElementById("pinLockScreen"),
    pinDots: document.querySelectorAll(".pin-dots .dot"),
    pinKeypad: document.querySelectorAll(".pin-keypad .key-btn"),
    pinError: document.getElementById("pinError"),

    toastContainer: document.getElementById("toastContainer")
  };

  // =========================================================================
  // 6. INITIAL SETUP & EVENT HANDLERS
  // =========================================================================
  function init() {
    applyTheme();
    applyLanguage();
    populateMonthSelectors();
    setupEventListeners();
    setupPinLock();
    checkApkStatus();
    render();

    // Register Service Worker for PWA
    if ("serviceWorker" in navigator) {
      navigator.serviceWorker.register("/sw.js").catch((err) => {
        console.log("Service Worker registration skipped:", err);
      });
    }
  }

  function applyTheme() {
    if (db.profile.isDarkMode) {
      DOM.body.classList.add("dark-theme");
      DOM.body.classList.remove("light-theme");
      DOM.themeDisplay.textContent = "☀️";
      if (DOM.settingThemeSelect) DOM.settingThemeSelect.value = "dark";
    } else {
      DOM.body.classList.add("light-theme");
      DOM.body.classList.remove("dark-theme");
      DOM.themeDisplay.textContent = "🌙";
      if (DOM.settingThemeSelect) DOM.settingThemeSelect.value = "light";
    }
  }

  function applyLanguage() {
    const lang = getActiveLang();
    DOM.langDisplay.textContent = lang === "ne" ? "🇳🇵" : "🇬🇧";
    if (DOM.settingLanguageSelect) DOM.settingLanguageSelect.value = lang;
    if (DOM.settingCalendarSelect) DOM.settingCalendarSelect.value = db.profile.calendarMode || "bs";

    // Update all data-i18n attributes
    document.querySelectorAll("[data-i18n]").forEach((el) => {
      const key = el.getAttribute("data-i18n");
      if (L10N[lang] && L10N[lang][key]) {
        el.textContent = L10N[lang][key];
      }
    });
  }

  function populateMonthSelectors() {
    const months = new Set();
    months.add(state.selectedMonth);

    // Collect all months from expenses and transactions
    db.personalExpenses.forEach((pe) => {
      if (pe.date && pe.date.length >= 7) months.add(pe.date.substring(0, 7));
    });
    db.groupTransactions.forEach((gt) => {
      if (gt.date && gt.date.length >= 7) months.add(gt.date.substring(0, 7));
    });
    Object.keys(db.budgets).forEach((m) => months.add(m));

    const sortedMonths = Array.from(months).sort().reverse();

    [DOM.activeMonthSelect, DOM.reportMonthSelect].forEach((select) => {
      if (!select) return;
      select.innerHTML = "";
      sortedMonths.forEach((m) => {
        const opt = document.createElement("option");
        opt.value = m;
        opt.textContent = m;
        if (m === state.selectedMonth) opt.selected = true;
        select.appendChild(opt);
      });
    });
  }

  function checkApkStatus() {
    fetch("/api/app-info")
      .then((res) => res.json())
      .then((data) => {
        if (data.apkAvailable) {
          const badge = document.getElementById("apkBadge");
          if (badge) badge.textContent = `APK (${data.apkSizeMb} MB)`;
        }
      })
      .catch(() => {});
  }

  // =========================================================================
  // 7. PIN LOCK SYSTEM
  // =========================================================================
  function setupPinLock() {
    if (!db.profile.securityPin) {
      DOM.pinLockScreen.classList.add("hidden");
      if (DOM.pinStatusDesc) DOM.pinStatusDesc.textContent = "PIN निष्क्रिय छ (Disabled)";
      return;
    }

    if (DOM.pinStatusDesc) DOM.pinStatusDesc.textContent = "PIN सक्रिय छ (Enabled)";
    if (state.isLocked) {
      DOM.pinLockScreen.classList.remove("hidden");
      updatePinDots();
    }
  }

  function updatePinDots() {
    DOM.pinDots.forEach((dot, index) => {
      if (index < state.pinBuffer.length) {
        dot.classList.add("filled");
      } else {
        dot.classList.remove("filled");
      }
    });
  }

  DOM.pinKeypad.forEach((btn) => {
    btn.addEventListener("click", () => {
      const key = btn.getAttribute("data-key");
      if (key === "backspace") {
        state.pinBuffer = state.pinBuffer.slice(0, -1);
        updatePinDots();
        DOM.pinError.classList.add("hidden");
        return;
      }

      if (state.pinBuffer.length < 4) {
        state.pinBuffer += key;
        updatePinDots();

        if (state.pinBuffer.length === 4) {
          if (state.pinBuffer === db.profile.securityPin) {
            state.isLocked = false;
            DOM.pinLockScreen.classList.add("hidden");
            state.pinBuffer = "";
            showToast("सफलतापूर्वक अनलक भयो (Unlocked)");
          } else {
            DOM.pinError.classList.remove("hidden");
            setTimeout(() => {
              state.pinBuffer = "";
              updatePinDots();
            }, 600);
          }
        }
      }
    });
  });

  // =========================================================================
  // 8. NAVIGATION ROUTING
  // =========================================================================
  function switchTab(tabId) {
    state.activeTab = tabId;

    // Update active tab buttons
    DOM.desktopTabs.forEach((tab) => {
      tab.classList.toggle("active", tab.getAttribute("data-tab") === tabId);
    });
    DOM.mobileTabs.forEach((tab) => {
      tab.classList.toggle("active", tab.getAttribute("data-tab") === tabId);
    });

    // Update views
    DOM.viewHome.classList.toggle("active", tabId === "home");
    DOM.viewPersonal.classList.toggle("active", tabId === "personal");
    DOM.viewGroup.classList.toggle("active", tabId === "group");
    DOM.viewReports.classList.toggle("active", tabId === "reports");

    window.scrollTo({ top: 0, behavior: "smooth" });
    render();
  }

  function switchGroupSubTab(subTabId) {
    state.activeGroupSubTab = subTabId;
    DOM.subtabs.forEach((tab) => {
      tab.classList.toggle("active", tab.getAttribute("data-subtab") === subTabId);
    });

    DOM.groupSubviewSummary.classList.toggle("active", subTabId === "summary");
    DOM.groupSubviewMembers.classList.toggle("active", subTabId === "members");
    DOM.groupSubviewTransactions.classList.toggle("active", subTabId === "transactions");

    renderGroupView();
  }

  // =========================================================================
  // 9. RENDERING VIEWS
  // =========================================================================
  function render() {
    renderHeaderProfile();
    renderHomeView();
    renderPersonalView();
    renderGroupView();
    renderReportsView();
  }

  function renderHeaderProfile() {
    DOM.homeUserName.textContent = db.profile.name || "User";
    DOM.homeUserAvatar.textContent = (db.profile.name || "U")[0].toUpperCase();
  }

  // 9.1 Home View
  function renderHomeView() {
    const currentMonth = state.selectedMonth;
    const monthlyLimit = db.budgets[currentMonth] || 25000;

    // Calculate monthly spent
    const monthlyExpenses = db.personalExpenses.filter((pe) => pe.date && pe.date.startsWith(currentMonth));
    const totalSpent = monthlyExpenses.reduce((sum, item) => sum + (Number(item.amount) || 0), 0);
    const remaining = monthlyLimit - totalSpent;
    const percentage = monthlyLimit > 0 ? Math.min(Math.round((totalSpent / monthlyLimit) * 100), 100) : 0;

    DOM.budgetLimitDisplay.textContent = formatNpr(monthlyLimit);
    DOM.budgetSpentDisplay.textContent = formatNpr(totalSpent);
    DOM.budgetRemainingDisplay.textContent = formatNpr(remaining);
    DOM.budgetProgressFill.style.width = `${percentage}%`;
    DOM.budgetPercentage.textContent = `${percentage}% खर्च भएको`;

    if (totalSpent > monthlyLimit) {
      DOM.budgetProgressFill.style.backgroundColor = "var(--danger)";
      DOM.budgetStatusText.textContent = "बजेट सीमा नाघ्यो!";
      DOM.budgetStatusText.className = "status-danger";
      DOM.overbudgetWarning.classList.remove("hidden");
    } else if (percentage >= 80) {
      DOM.budgetProgressFill.style.backgroundColor = "var(--warning)";
      DOM.budgetStatusText.textContent = "८०% भन्दा बढी खर्च भइसक्यो";
      DOM.budgetStatusText.className = "status-warning";
      DOM.overbudgetWarning.classList.add("hidden");
    } else {
      DOM.budgetProgressFill.style.backgroundColor = "var(--primary)";
      DOM.budgetStatusText.textContent = "सुरक्षित दायरामा";
      DOM.budgetStatusText.className = "status-safe";
      DOM.overbudgetWarning.classList.add("hidden");
    }

    // Group Snapshot
    const balances = SettlementEngine.calculateBalances(db.groupMembers, db.groupTransactions);
    const settlements = SettlementEngine.calculateSettlements(db.groupMembers, db.groupTransactions);

    if (settlements.length === 0) {
      DOM.homeGroupSummaryContent.innerHTML = `
        <div class="empty-state-mini">
          <span>✅</span>
          <p>सबै हिसाब बराबर छ! कुनै ऋण बाँकी छैन।</p>
        </div>
      `;
    } else {
      DOM.homeGroupSummaryContent.innerHTML = settlements.slice(0, 2).map((s) => `
        <div class="settlement-card" style="margin-bottom: 8px;">
          <div class="settlement-text">
            <strong>${escapeHtml(s.debtorName)}</strong> ले <strong>${escapeHtml(s.creditorName)}</strong> लाई दिनुपर्ने
          </div>
          <span class="settlement-amount">${formatNpr(s.amount)}</span>
        </div>
      `).join("");
    }

    // Recent Personal List
    const recent = [...db.personalExpenses]
      .sort((a, b) => (b.date + b.time).localeCompare(a.date + a.time))
      .slice(0, 4);

    if (recent.length === 0) {
      DOM.homeRecentList.innerHTML = `<div class="empty-state-mini"><p>${t("noRecent")}</p></div>`;
    } else {
      DOM.homeRecentList.innerHTML = recent.map((item) => renderPersonalCardHtml(item)).join("");
    }
  }

  // 9.2 Personal Expenses View (Complete History across all months/years)
  function renderPersonalView() {
    let filtered = [...db.personalExpenses];

    // Search filter
    if (state.personalSearchQuery) {
      const q = state.personalSearchQuery.toLowerCase();
      filtered = filtered.filter((item) =>
        (item.title && item.title.toLowerCase().includes(q)) ||
        (item.description && item.description.toLowerCase().includes(q)) ||
        (item.notes && item.notes.toLowerCase().includes(q))
      );
    }

    // Category filter
    if (state.personalCategoryFilter !== "all") {
      filtered = filtered.filter((item) => item.category && item.category.toLowerCase() === state.personalCategoryFilter.toLowerCase());
    }

    // Sorting
    filtered.sort((a, b) => {
      if (state.personalSortOrder === "date_desc") return (b.date + b.time).localeCompare(a.date + a.time);
      if (state.personalSortOrder === "date_asc") return (a.date + a.time).localeCompare(b.date + b.time);
      if (state.personalSortOrder === "amount_desc") return b.amount - a.amount;
      if (state.personalSortOrder === "amount_asc") return a.amount - b.amount;
      return 0;
    });

    const totalAmount = filtered.reduce((sum, item) => sum + (Number(item.amount) || 0), 0);
    DOM.personalHistoryCount.textContent = `${t("historyCount")} (${filtered.length})`;
    DOM.personalHistoryTotal.textContent = `${t("total")}: ${formatNpr(totalAmount)}`;

    if (filtered.length === 0) {
      DOM.personalExpenseList.innerHTML = `
        <div class="empty-state">
          <span style="font-size: 2.5rem;">📝</span>
          <p>कुनै खर्च रेकर्ड भेटिएन। "+ खर्च थप्नुहोस्" बटन क्लिक गरी थप्नुहोस्।</p>
        </div>
      `;
    } else {
      DOM.personalExpenseList.innerHTML = filtered.map((item) => renderPersonalCardHtml(item)).join("");
    }
  }

  function renderPersonalCardHtml(item) {
    const icon = CATEGORY_ICONS[item.category] || "🏷️";
    return `
      <div class="tx-card" data-id="${item.id}">
        <div class="tx-card-left">
          <div class="tx-category-icon-badge">${icon}</div>
          <div class="tx-info">
            <div class="tx-title">${escapeHtml(item.title)}</div>
            <div class="tx-meta">
              <span>📅 ${formatDisplayDate(item.date)}</span>
              ${item.time ? `<span>⏱️ ${escapeHtml(item.time)}</span>` : ""}
              <span>• ${escapeHtml(item.category)}</span>
            </div>
            ${item.description ? `<div class="tx-desc">${escapeHtml(item.description)}</div>` : ""}
          </div>
        </div>
        <div class="tx-card-right">
          <span class="tx-amount">${formatNpr(item.amount)}</span>
          <div class="tx-actions">
            <button class="tx-action-btn btn-bill" onclick="window.AppActions.viewPersonalBill(${item.id})" title="Generate Bill">
              📄 रसिद
            </button>
            <button class="tx-action-btn btn-edit" onclick="window.AppActions.editPersonalExpense(${item.id})" title="Edit">
              ✏️
            </button>
            <button class="tx-action-btn btn-delete" onclick="window.AppActions.deletePersonalExpense(${item.id})" title="Delete">
              🗑️
            </button>
          </div>
        </div>
      </div>
    `;
  }

  // 9.3 Group View
  function renderGroupView() {
    const totalSpent = db.groupTransactions.reduce((sum, item) => sum + (Number(item.amount) || 0), 0);
    DOM.groupTotalSpent.textContent = formatNpr(totalSpent);
    DOM.groupMemberCount.textContent = `${db.groupMembers.length} जना`;

    if (state.activeGroupSubTab === "summary") {
      const balances = SettlementEngine.calculateBalances(db.groupMembers, db.groupTransactions);
      const settlements = SettlementEngine.calculateSettlements(db.groupMembers, db.groupTransactions);

      // Balances
      DOM.groupBalancesList.innerHTML = db.groupMembers.map((m) => {
        const bal = balances[m.id] || 0;
        let badgeClass = "balance-settled";
        let signText = "हिसाब बराबर";
        if (bal > 0.01) {
          badgeClass = "balance-surplus";
          signText = `+${formatNpr(bal)} (लिनुपर्ने)`;
        } else if (bal < -0.01) {
          badgeClass = "balance-deficit";
          signText = `${formatNpr(bal)} (तिर्नुपर्ने)`;
        }

        return `
          <div class="balance-item-card">
            <span class="balance-member-name">${escapeHtml(m.name)}</span>
            <span class="balance-badge ${badgeClass}">${signText}</span>
          </div>
        `;
      }).join("");

      // Settlements
      if (settlements.length === 0) {
        DOM.groupSettlementsList.innerHTML = `
          <div class="empty-state-mini">
            <span>🎉</span>
            <p>सबै सदस्यहरूको हिसाब चुक्ता छ!</p>
          </div>
        `;
      } else {
        DOM.groupSettlementsList.innerHTML = settlements.map((s, idx) => `
          <div class="settlement-card">
            <div class="settlement-text">
              <strong>${escapeHtml(s.debtorName)}</strong> ले <strong>${escapeHtml(s.creditorName)}</strong> लाई दिनुपर्ने:
            </div>
            <div style="display: flex; align-items: center; gap: 8px;">
              <span class="settlement-amount">${formatNpr(s.amount)}</span>
              <button class="btn-settle-action" onclick="window.AppActions.markSettlementCleared(${s.debtorId}, ${s.creditorId}, ${s.amount})">
                ✓ ${t("markSettled")}
              </button>
            </div>
          </div>
        `).join("");
      }
    } else if (state.activeGroupSubTab === "members") {
      if (db.groupMembers.length === 0) {
        DOM.groupMembersList.innerHTML = `<div class="empty-state"><p>कुनै सदस्य थपिएको छैन।</p></div>`;
      } else {
        DOM.groupMembersList.innerHTML = db.groupMembers.map((m) => `
          <div class="member-card">
            <div class="member-card-info">
              <div class="member-avatar" style="background-color: ${m.avatarColor || 'var(--primary-light)'}; color: #FFFFFF;">
                ${(m.name || "M")[0].toUpperCase()}
              </div>
              <div>
                <div class="member-name">${escapeHtml(m.name)}</div>
                ${m.phone ? `<div class="member-phone">📞 ${escapeHtml(m.phone)}</div>` : ""}
                ${m.notes ? `<div class="member-phone">📝 ${escapeHtml(m.notes)}</div>` : ""}
              </div>
            </div>
            <div class="tx-actions">
              <button class="tx-action-btn btn-edit" onclick="window.AppActions.editMember(${m.id})">✏️</button>
              <button class="tx-action-btn btn-delete" onclick="window.AppActions.deleteMember(${m.id})">🗑️</button>
            </div>
          </div>
        `).join("");
      }
    } else if (state.activeGroupSubTab === "transactions") {
      let txs = [...db.groupTransactions];
      if (state.groupSearchQuery) {
        const q = state.groupSearchQuery.toLowerCase();
        txs = txs.filter((t) => (t.name && t.name.toLowerCase().includes(q)) || (t.notes && t.notes.toLowerCase().includes(q)));
      }
      txs.sort((a, b) => (b.date + b.time).localeCompare(a.date + a.time));

      if (txs.length === 0) {
        DOM.groupTransactionsList.innerHTML = `<div class="empty-state"><p>कुनै लेनदेन भेटिएन।</p></div>`;
      } else {
        DOM.groupTransactionsList.innerHTML = txs.map((tx) => {
          const payer = db.groupMembers.find((m) => m.id === tx.paidById);
          const splitIds = (tx.splitWithIds || "").split(",").filter(Boolean).map((id) => parseInt(id, 10));
          const splitMembers = db.groupMembers.filter((m) => splitIds.includes(m.id));
          const perPerson = splitIds.length > 0 ? tx.amount / splitIds.length : 0;

          return `
            <div class="tx-card">
              <div class="tx-card-left">
                <div class="tx-category-icon-badge">👥</div>
                <div class="tx-info">
                  <div class="tx-title">${escapeHtml(tx.name)}</div>
                  <div class="tx-meta">
                    <span>💳 ${payer ? escapeHtml(payer.name) : "अज्ञात"} ले तिर्यो</span>
                    <span>• 📅 ${formatDisplayDate(tx.date)}</span>
                    <span>• ${splitMembers.length} जनामा भाग (${formatNpr(perPerson)}/जना)</span>
                  </div>
                  <div class="tx-desc">
                    बाँडफाँड: ${splitMembers.map((m) => escapeHtml(m.name)).join(", ")}
                  </div>
                </div>
              </div>
              <div class="tx-card-right">
                <span class="tx-amount">${formatNpr(tx.amount)}</span>
                <div class="tx-actions">
                  <button class="tx-action-btn btn-bill" onclick="window.AppActions.viewGroupBill(${tx.id})">
                    📄 बिल
                  </button>
                  <button class="tx-action-btn btn-edit" onclick="window.AppActions.editGroupTx(${tx.id})">
                    ✏️
                  </button>
                  <button class="tx-action-btn btn-delete" onclick="window.AppActions.deleteGroupTx(${tx.id})">
                    🗑️
                  </button>
                </div>
              </div>
            </div>
          `;
        }).join("");
      }
    }
  }

  // 9.4 Reports View
  function renderReportsView() {
    const reportMonth = DOM.reportMonthSelect.value || state.selectedMonth;
    const monthlyExpenses = db.personalExpenses.filter((pe) => pe.date && pe.date.startsWith(reportMonth));
    const totalSpent = monthlyExpenses.reduce((sum, item) => sum + (Number(item.amount) || 0), 0);

    DOM.reportMonthTotal.textContent = formatNpr(totalSpent);

    // Group by category
    const catTotals = {};
    monthlyExpenses.forEach((pe) => {
      const cat = pe.category || "Others";
      catTotals[cat] = (catTotals[cat] || 0) + pe.amount;
    });

    const sortedCats = Object.entries(catTotals).sort((a, b) => b[1] - a[1]);

    if (sortedCats.length === 0) {
      DOM.categoryAnalyticsBars.innerHTML = `<div class="empty-state-mini"><p>यस महिनाको कुनै खर्च छैन।</p></div>`;
    } else {
      DOM.categoryAnalyticsBars.innerHTML = sortedCats.map(([cat, amount]) => {
        const pct = totalSpent > 0 ? Math.round((amount / totalSpent) * 100) : 0;
        const icon = CATEGORY_ICONS[cat] || "🏷️";
        return `
          <div class="cat-bar-item">
            <div class="cat-bar-header">
              <span>${icon} ${escapeHtml(cat)}</span>
              <span><strong>${formatNpr(amount)}</strong> (${pct}%)</span>
            </div>
            <div class="cat-bar-track">
              <div class="cat-bar-fill" style="width: ${pct}%;"></div>
            </div>
          </div>
        `;
      }).join("");
    }
  }

  // =========================================================================
  // 10. ACTIONS & MODAL CONTROLLERS (Global window.AppActions)
  // =========================================================================
  window.AppActions = {
    // Personal Expense
    openAddPersonalModal() {
      DOM.peId.value = "0";
      DOM.personalExpenseForm.reset();
      DOM.peDate.value = new Date().toISOString().split("T")[0];
      DOM.peTime.value = new Date().toTimeString().slice(0, 5);
      DOM.personalExpenseModal.classList.remove("hidden");
    },

    editPersonalExpense(id) {
      const item = db.personalExpenses.find((pe) => pe.id === id);
      if (!item) return;

      DOM.peId.value = String(item.id);
      DOM.peTitle.value = item.title || "";
      DOM.peAmount.value = item.amount || "";
      DOM.peCategory.value = item.category || "Food";
      DOM.peDate.value = item.date || "";
      DOM.peTime.value = item.time || "";
      DOM.peDescription.value = item.description || "";
      DOM.peNotes.value = item.notes || "";

      DOM.personalExpenseModal.classList.remove("hidden");
    },

    deletePersonalExpense(id) {
      promptSecureDelete(() => {
        db.personalExpenses = db.personalExpenses.filter((pe) => pe.id !== id);
        saveDatabase(db);
        populateMonthSelectors();
        render();
        showToast("खर्च रेकर्ड सफलतापूर्वक मेटियो (Deleted)");
      });
    },

    viewPersonalBill(id) {
      const item = db.personalExpenses.find((pe) => pe.id === id);
      if (!item) return;

      DOM.billPaperContent.innerHTML = `
        <div class="bill-header-row">
          <div class="bill-org">
            <h2>अर्थ मन्त्रालय (Artha Mantralaya)</h2>
            <p>आधिकारिक व्यक्तिगत खर्च रसिद • Official Receipt</p>
          </div>
          <img src="/icons/icon.svg" width="54" height="54" alt="Logo">
        </div>

        <div class="bill-meta-grid">
          <div><strong>रसिद नं (Bill No):</strong> #${String(item.id).padStart(6, "0")}</div>
          <div><strong>मिति (Date):</strong> ${formatDisplayDate(item.date)} ${item.time || ""}</div>
          <div><strong>खर्चकर्ता (User):</strong> ${escapeHtml(db.profile.name)}</div>
          <div><strong>विधा (Category):</strong> ${escapeHtml(item.category)}</div>
        </div>

        <table class="bill-table">
          <thead>
            <tr>
              <th>शीर्षक र विवरण (Description)</th>
              <th style="text-align: right;">रकम (Amount)</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <strong>${escapeHtml(item.title)}</strong>
                ${item.description ? `<br><small style="color: #64748B;">${escapeHtml(item.description)}</small>` : ""}
                ${item.notes ? `<br><small style="color: #64748B;">टिप्पणी: ${escapeHtml(item.notes)}</small>` : ""}
              </td>
              <td style="text-align: right; font-weight: 700;">${formatNpr(item.amount)}</td>
            </tr>
          </tbody>
        </table>

        <div class="bill-total-box">
          <div class="bill-total-text">जम्मा रकम (Total): ${formatNpr(item.amount)}</div>
        </div>
      `;

      DOM.billViewerModal.classList.remove("hidden");
    },

    // Group Transactions
    openAddGroupTxModal() {
      if (db.groupMembers.length === 0) {
        showToast("कृपया पहिले कम्तीमा एक सदस्य थप्नुहोस्!");
        switchGroupSubTab("members");
        return;
      }

      DOM.gtId.value = "0";
      DOM.groupTxForm.reset();
      DOM.gtDate.value = new Date().toISOString().split("T")[0];
      DOM.gtTime.value = new Date().toTimeString().slice(0, 5);

      // Populate Payer Dropdown
      DOM.gtPaidBy.innerHTML = db.groupMembers.map((m) => `
        <option value="${m.id}">${escapeHtml(m.name)}</option>
      `).join("");

      // Populate Split Checkboxes
      DOM.splitMembersCheckboxes.innerHTML = db.groupMembers.map((m) => `
        <label class="split-member-check-item">
          <input type="checkbox" name="splitMembers" value="${m.id}" checked>
          <span>${escapeHtml(m.name)}</span>
        </label>
      `).join("");

      updateGroupSplitCalcPreview();
      DOM.groupTxModal.classList.remove("hidden");
    },

    editGroupTx(id) {
      const tx = db.groupTransactions.find((t) => t.id === id);
      if (!tx) return;

      DOM.gtId.value = String(tx.id);
      DOM.gtName.value = tx.name || "";
      DOM.gtAmount.value = tx.amount || "";
      DOM.gtDate.value = tx.date || "";
      DOM.gtTime.value = tx.time || "";
      DOM.gtNotes.value = tx.notes || "";

      DOM.gtPaidBy.innerHTML = db.groupMembers.map((m) => `
        <option value="${m.id}" ${m.id === tx.paidById ? "selected" : ""}>${escapeHtml(m.name)}</option>
      `).join("");

      const splitIds = (tx.splitWithIds || "").split(",").filter(Boolean).map((s) => parseInt(s, 10));
      DOM.splitMembersCheckboxes.innerHTML = db.groupMembers.map((m) => `
        <label class="split-member-check-item">
          <input type="checkbox" name="splitMembers" value="${m.id}" ${splitIds.includes(m.id) ? "checked" : ""}>
          <span>${escapeHtml(m.name)}</span>
        </label>
      `).join("");

      updateGroupSplitCalcPreview();
      DOM.groupTxModal.classList.remove("hidden");
    },

    deleteGroupTx(id) {
      promptSecureDelete(() => {
        db.groupTransactions = db.groupTransactions.filter((t) => t.id !== id);
        saveDatabase(db);
        populateMonthSelectors();
        render();
        showToast("समूह लेनदेन मेटियो (Deleted)");
      });
    },

    viewGroupBill(id) {
      const tx = db.groupTransactions.find((t) => t.id === id);
      if (!tx) return;

      const payer = db.groupMembers.find((m) => m.id === tx.paidById);
      const splitIds = (tx.splitWithIds || "").split(",").filter(Boolean).map((id) => parseInt(id, 10));
      const splitMembers = db.groupMembers.filter((m) => splitIds.includes(m.id));
      const perPerson = splitIds.length > 0 ? tx.amount / splitIds.length : 0;

      DOM.billPaperContent.innerHTML = `
        <div class="bill-header-row">
          <div class="bill-org">
            <h2>अर्थ मन्त्रालय • समूह रसिद</h2>
            <p>Group Expense & Split Statement</p>
          </div>
          <img src="/icons/icon.svg" width="54" height="54" alt="Logo">
        </div>

        <div class="bill-meta-grid">
          <div><strong>लेनदेन नं:</strong> #GT-${String(tx.id).padStart(5, "0")}</div>
          <div><strong>मिति (Date):</strong> ${formatDisplayDate(tx.date)} ${tx.time || ""}</div>
          <div><strong>तिर्ने व्यक्ति (Paid By):</strong> ${payer ? escapeHtml(payer.name) : "अज्ञात"}</div>
          <div><strong>बाँडफाँड संख्या:</strong> ${splitMembers.length} जना</div>
        </div>

        <table class="bill-table">
          <thead>
            <tr>
              <th>शीर्षक (Item)</th>
              <th style="text-align: right;">कुल रकम</th>
              <th style="text-align: right;">प्रति व्यक्ति</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <strong>${escapeHtml(tx.name)}</strong>
                ${tx.notes ? `<br><small style="color: #64748B;">टिप्पणी: ${escapeHtml(tx.notes)}</small>` : ""}
              </td>
              <td style="text-align: right; font-weight: 700;">${formatNpr(tx.amount)}</td>
              <td style="text-align: right;">${formatNpr(perPerson)}</td>
            </tr>
          </tbody>
        </table>

        <div style="margin-top: 14px; font-size: 0.85rem; background: #F8FAFC; padding: 10px 14px; border-radius: 6px;">
          <strong>बाँडफाँड गरिएका सदस्यहरू:</strong>
          <ul style="margin: 6px 0 0 18px; line-height: 1.6;">
            ${splitMembers.map((m) => `<li>${escapeHtml(m.name)} - ${formatNpr(perPerson)}</li>`).join("")}
          </ul>
        </div>

        <div class="bill-total-box">
          <div class="bill-total-text">कुल रकम: ${formatNpr(tx.amount)}</div>
        </div>
      `;

      DOM.billViewerModal.classList.remove("hidden");
    },

    markSettlementCleared(debtorId, creditorId, amount) {
      const debtor = db.groupMembers.find((m) => m.id === debtorId);
      const creditor = db.groupMembers.find((m) => m.id === creditorId);

      const newTx = {
        id: Date.now(),
        name: `फरफारक चुक्ता: ${debtor ? debtor.name : debtorId} -> ${creditor ? creditor.name : creditorId}`,
        amount: amount,
        date: new Date().toISOString().split("T")[0],
        time: new Date().toTimeString().slice(0, 5),
        paidById: debtorId,
        splitWithIds: String(creditorId),
        notes: "स्वचालित फरफारक समायोजन"
      };

      db.groupTransactions.push(newTx);
      saveDatabase(db);
      render();
      showToast("हिसाब चुक्ता रेकर्ड थपियो! फरफारक अद्यावधिक भयो।");
    },

    // Group Members
    openAddMemberModal() {
      DOM.gmId.value = "0";
      DOM.groupMemberForm.reset();
      DOM.groupMemberModal.classList.remove("hidden");
    },

    editMember(id) {
      const m = db.groupMembers.find((mem) => mem.id === id);
      if (!m) return;

      DOM.gmId.value = String(m.id);
      DOM.gmName.value = m.name || "";
      DOM.gmPhone.value = m.phone || "";
      DOM.gmNotes.value = m.notes || "";
      DOM.groupMemberModal.classList.remove("hidden");
    },

    deleteMember(id) {
      // Check if involved in transactions
      const inTx = db.groupTransactions.some((t) => t.paidById === id || (t.splitWithIds && t.splitWithIds.includes(String(id))));
      if (inTx) {
        alert("यो सदस्य लेनदेनमा समावेश हुनुहुन्छ! पहिले लेनदेन हटाउनुहोस् वा फरफारक गर्नुहोस्।");
        return;
      }

      promptSecureDelete(() => {
        db.groupMembers = db.groupMembers.filter((m) => m.id !== id);
        saveDatabase(db);
        render();
        showToast("सदस्य हटाइयो (Member removed)");
      });
    }
  };

  function updateGroupSplitCalcPreview() {
    const amt = parseFloat(DOM.gtAmount.value) || 0;
    const checked = DOM.splitMembersCheckboxes.querySelectorAll("input:checked").length;
    const share = checked > 0 ? amt / checked : 0;
    DOM.splitCalcPreview.innerHTML = `<span>छानिएको सदस्य: <strong>${checked}</strong> | प्रति व्यक्ति: <strong>${formatNpr(share)}</strong></span>`;
  }

  function promptSecureDelete(callback) {
    state.deletePendingCallback = callback;
    DOM.secureDeletePasswordInput.value = "";
    DOM.confirmSecureDeleteBtn.disabled = true;
    DOM.secureDeleteModal.classList.remove("hidden");
  }

  DOM.secureDeletePasswordInput.addEventListener("input", (e) => {
    DOM.confirmSecureDeleteBtn.disabled = e.target.value !== "100";
  });

  DOM.confirmSecureDeleteBtn.addEventListener("click", () => {
    if (DOM.secureDeletePasswordInput.value === "100" && state.deletePendingCallback) {
      state.deletePendingCallback();
      state.deletePendingCallback = null;
      DOM.secureDeleteModal.classList.add("hidden");
    }
  });

  // =========================================================================
  // 11. FORM SUBMISSIONS
  // =========================================================================
  // Personal Expense Submit
  DOM.personalExpenseForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = parseInt(DOM.peId.value, 10);
    const title = DOM.peTitle.value.trim();
    const amount = parseFloat(DOM.peAmount.value);
    const category = DOM.peCategory.value;
    const date = DOM.peDate.value;
    const time = DOM.peTime.value;
    const description = DOM.peDescription.value.trim();
    const notes = DOM.peNotes.value.trim();

    if (!title || isNaN(amount) || amount <= 0 || !date) {
      alert("कृपया आवश्यक सबै विवरण भर्नुहोस्।");
      return;
    }

    if (id === 0) {
      // Create new
      const newPe = {
        id: Date.now(),
        title,
        amount,
        category,
        date,
        time,
        description,
        notes
      };
      db.personalExpenses.push(newPe);
      showToast("नयाँ खर्च सुरक्षित भयो (Saved)");
    } else {
      // Update existing
      const index = db.personalExpenses.findIndex((p) => p.id === id);
      if (index !== -1) {
        db.personalExpenses[index] = {
          ...db.personalExpenses[index],
          title,
          amount,
          category,
          date,
          time,
          description,
          notes
        };
        showToast("खर्च अद्यावधिक भयो (Updated)");
      }
    }

    saveDatabase(db);
    populateMonthSelectors();
    DOM.personalExpenseModal.classList.add("hidden");
    render();
  });

  // Group Transaction Submit
  DOM.groupTxForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = parseInt(DOM.gtId.value, 10);
    const name = DOM.gtName.value.trim();
    const amount = parseFloat(DOM.gtAmount.value);
    const paidById = parseInt(DOM.gtPaidBy.value, 10);
    const date = DOM.gtDate.value;
    const time = DOM.gtTime.value;
    const notes = DOM.gtNotes.value.trim();

    const checkedBoxes = Array.from(DOM.splitMembersCheckboxes.querySelectorAll("input:checked"));
    const splitWithIds = checkedBoxes.map((cb) => cb.value).join(",");

    if (!name || isNaN(amount) || amount <= 0 || !date || checkedBoxes.length === 0) {
      alert("कृपया शीर्षक, रकम र कम्तीमा एक बाँडफाँड सदस्य चयन गर्नुहोस्।");
      return;
    }

    if (id === 0) {
      const newGt = {
        id: Date.now(),
        name,
        amount,
        paidById,
        splitWithIds,
        date,
        time,
        notes
      };
      db.groupTransactions.push(newGt);
      showToast("समूह लेनदेन सुरक्षित भयो (Saved)");
    } else {
      const index = db.groupTransactions.findIndex((t) => t.id === id);
      if (index !== -1) {
        db.groupTransactions[index] = {
          ...db.groupTransactions[index],
          name,
          amount,
          paidById,
          splitWithIds,
          date,
          time,
          notes
        };
        showToast("समूह लेनदेन अद्यावधिक भयो (Updated)");
      }
    }

    saveDatabase(db);
    populateMonthSelectors();
    DOM.groupTxModal.classList.add("hidden");
    render();
  });

  // Group Member Submit
  DOM.groupMemberForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = parseInt(DOM.gmId.value, 10);
    const name = DOM.gmName.value.trim();
    const phone = DOM.gmPhone.value.trim();
    const notes = DOM.gmNotes.value.trim();

    if (!name) {
      alert("कृपया सदस्यको नाम भर्नुहोस्।");
      return;
    }

    const colors = ["#1976D2", "#388E3C", "#D97706", "#7C3AED", "#DB2777", "#059669"];
    const avatarColor = colors[Math.floor(Math.random() * colors.length)];

    if (id === 0) {
      const newGm = {
        id: Date.now(),
        name,
        phone,
        notes,
        avatarColor
      };
      db.groupMembers.push(newGm);
      showToast("नयाँ सदस्य थपियो (Member added)");
    } else {
      const index = db.groupMembers.findIndex((m) => m.id === id);
      if (index !== -1) {
        db.groupMembers[index] = {
          ...db.groupMembers[index],
          name,
          phone,
          notes
        };
        showToast("सदस्य विवरण अद्यावधिक भयो");
      }
    }

    saveDatabase(db);
    DOM.groupMemberModal.classList.add("hidden");
    render();
  });

  // Set Budget Form Submit
  DOM.budgetForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const month = DOM.budgetMonthInput.value;
    const amount = parseFloat(DOM.budgetAmountInput.value);

    if (!month || isNaN(amount) || amount < 0) {
      alert("कृपया वैध महिना र बजेट रकम प्रविष्ट गर्नुहोस्।");
      return;
    }

    db.budgets[month] = amount;
    state.selectedMonth = month;
    saveDatabase(db);
    populateMonthSelectors();
    DOM.budgetModal.classList.add("hidden");
    render();
    showToast("बजेट सफलतापूर्वक सेट भयो (Budget updated)");
  });

  // Profile Form Submit
  DOM.profileForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const name = DOM.profileNameInput.value.trim();
    if (!name) return;

    db.profile.name = name;
    saveDatabase(db);
    DOM.profileModal.classList.add("hidden");
    render();
    showToast("प्रोफाइल अद्यावधिक भयो (Profile updated)");
  });

  // Custom Report Form Submit
  DOM.customReportForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const filterType = DOM.crFilterType.value;
    const format = DOM.customReportForm.elements["crFormat"].value;

    let filtered = [...db.groupTransactions];
    let periodLabel = "All Time (सबै समय)";

    if (filterType === "month") {
      const m = DOM.crMonthKey.value;
      if (!m) return alert("कृपया महिना छान्नुहोस्");
      filtered = filtered.filter((t) => t.date && t.date.startsWith(m));
      periodLabel = `Month: ${m}`;
    } else if (filterType === "year") {
      const y = DOM.crYearKey.value;
      if (!y) return alert("कृपया वर्ष छान्नुहोस्");
      filtered = filtered.filter((t) => t.date && t.date.startsWith(y));
      periodLabel = `Year: ${y}`;
    } else if (filterType === "custom") {
      const s = DOM.crStartDate.value;
      const end = DOM.crEndDate.value;
      if (!s || !end) return alert("कृपया सुरु र अन्त्य मिति छान्नुहोस्");
      filtered = filtered.filter((t) => t.date >= s && t.date <= end);
      periodLabel = `${s} to ${end}`;
    }

    DOM.customReportModal.classList.add("hidden");

    if (format === "csv") {
      downloadGroupReportCsv(filtered, periodLabel);
    } else if (format === "excel") {
      downloadGroupReportExcel(filtered, periodLabel);
    } else {
      viewGroupReportPdf(filtered, periodLabel);
    }
  });

  // =========================================================================
  // 12. REPORT EXPORTERS (CSV, EXCEL, PRINT/PDF)
  // =========================================================================
  function downloadGroupReportCsv(txs, period) {
    let csv = `Artha Mantralaya - Group Custom Report\nPeriod: ${period}\nGenerated: ${new Date().toLocaleString()}\n\n`;
    csv += "ID,Title,Amount (NPR),Date,Time,Paid By,Split With,Notes\n";

    txs.forEach((t) => {
      const payer = db.groupMembers.find((m) => m.id === t.paidById);
      const splitIds = (t.splitWithIds || "").split(",").filter(Boolean).map((id) => parseInt(id, 10));
      const splitNames = db.groupMembers.filter((m) => splitIds.includes(m.id)).map((m) => m.name).join("; ");

      csv += `"${t.id}","${(t.name || "").replace(/"/g, '""')}","${t.amount}","${t.date}","${t.time}","${payer ? payer.name : ""}","${splitNames}","${(t.notes || "").replace(/"/g, '""')}"\n`;
    });

    const blob = new Blob([csv], { type: "text/csv;charset=utf-8;" });
    downloadBlob(blob, `artha_group_report_${period.replace(/[^a-zA-Z0-9]/g, "_")}.csv`);
    showToast("CSV रिपोर्ट डाउनलोड भयो (Downloaded)");
  }

  function downloadGroupReportExcel(txs, period) {
    let html = `
      <html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel">
      <head><meta charset="utf-8"></head>
      <body>
        <h2>अर्थ मन्त्रालय - समूह हिसाब रिपोर्ट</h2>
        <p><strong>Period:</strong> ${period}</p>
        <p><strong>Generated:</strong> ${new Date().toLocaleString()}</p>
        <table border="1">
          <tr style="background:#1976D2; color:#FFFFFF;">
            <th>ID</th><th>खर्च शीर्षक</th><th>रकम (रु)</th><th>मिति</th><th>तिर्ने व्यक्ति</th><th>बाँडफाँड गरिएका सदस्य</th><th>टिप्पणी</th>
          </tr>
    `;

    txs.forEach((t) => {
      const payer = db.groupMembers.find((m) => m.id === t.paidById);
      const splitIds = (t.splitWithIds || "").split(",").filter(Boolean).map((id) => parseInt(id, 10));
      const splitNames = db.groupMembers.filter((m) => splitIds.includes(m.id)).map((m) => m.name).join(", ");
      html += `
        <tr>
          <td>${t.id}</td>
          <td>${escapeHtml(t.name)}</td>
          <td>${t.amount}</td>
          <td>${t.date}</td>
          <td>${payer ? escapeHtml(payer.name) : ""}</td>
          <td>${escapeHtml(splitNames)}</td>
          <td>${escapeHtml(t.notes || "")}</td>
        </tr>
      `;
    });

    html += "</table></body></html>";

    const blob = new Blob([html], { type: "application/vnd.ms-excel;charset=utf-8;" });
    downloadBlob(blob, `artha_group_report_${period.replace(/[^a-zA-Z0-9]/g, "_")}.xls`);
    showToast("Excel रिपोर्ट डाउनलोड भयो (Downloaded)");
  }

  function viewGroupReportPdf(txs, period) {
    const total = txs.reduce((sum, t) => sum + t.amount, 0);
    const balances = SettlementEngine.calculateBalances(db.groupMembers, txs);
    const settlements = SettlementEngine.calculateSettlements(db.groupMembers, txs);

    DOM.billPaperContent.innerHTML = `
      <div class="bill-header-row">
        <div class="bill-org">
          <h2>अर्थ मन्त्रालय • समूह हिसाब आधिकारिक प्रतिवेदन</h2>
          <p>Comprehensive Group Financial Audit Report</p>
        </div>
        <img src="/icons/icon.svg" width="54" height="54" alt="Logo">
      </div>

      <div class="bill-meta-grid">
        <div><strong>समय दायरा (Period):</strong> ${escapeHtml(period)}</div>
        <div><strong>तयार मिति:</strong> ${new Date().toLocaleString()}</div>
        <div><strong>जम्मा लेनदेन संख्या:</strong> ${txs.length} वटा</div>
        <div><strong>कुल समूह खर्च:</strong> ${formatNpr(total)}</div>
      </div>

      <h4 style="margin-top: 14px; font-size: 0.95rem;">१. विस्तृत कारोबार सूची (Transaction List)</h4>
      <table class="bill-table">
        <thead>
          <tr>
            <th>मिति</th>
            <th>शीर्षक</th>
            <th>तिर्ने व्यक्ति</th>
            <th style="text-align: right;">रकम</th>
          </tr>
        </thead>
        <tbody>
          ${txs.map((t) => {
            const payer = db.groupMembers.find((m) => m.id === t.paidById);
            return `
              <tr>
                <td>${t.date}</td>
                <td>${escapeHtml(t.name)}</td>
                <td>${payer ? escapeHtml(payer.name) : ""}</td>
                <td style="text-align: right; font-weight: 700;">${formatNpr(t.amount)}</td>
              </tr>
            `;
          }).join("")}
        </tbody>
      </table>

      <h4 style="margin-top: 16px; font-size: 0.95rem;">२. सदस्यहरूको खुद हिसाब (Net Balances)</h4>
      <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px; font-size: 0.85rem;">
        ${db.groupMembers.map((m) => {
          const bal = balances[m.id] || 0;
          return `
            <div style="background: #F8FAFC; padding: 8px 12px; border-radius: 6px; display: flex; justify-content: space-between;">
              <span>${escapeHtml(m.name)}:</span>
              <strong style="color: ${bal > 0 ? '#16A34A' : bal < 0 ? '#DC2626' : '#64748B'};">${formatNpr(bal)}</strong>
            </div>
          `;
        }).join("")}
      </div>

      <h4 style="margin-top: 16px; font-size: 0.95rem;">३. ऋण फरफारक योजना (Settlement Plan)</h4>
      <div style="font-size: 0.85rem;">
        ${settlements.length === 0 ? "<p>कुनै ऋण बाँकी छैन।</p>" : settlements.map((s) => `
          <div style="padding: 6px 0; border-bottom: 1px dashed #E2E8F0;">
            • <strong>${escapeHtml(s.debtorName)}</strong> ले <strong>${escapeHtml(s.creditorName)}</strong> लाई <strong>${formatNpr(s.amount)}</strong> तिर्नुपर्ने।
          </div>
        `).join("")}
      </div>

      <div class="bill-total-box">
        <div class="bill-total-text">कुल समूह कारोबार: ${formatNpr(total)}</div>
      </div>
    `;

    DOM.billViewerModal.classList.remove("hidden");
  }

  function downloadMonthlyPersonalBillPdf() {
    const reportMonth = DOM.reportMonthSelect.value || state.selectedMonth;
    const monthlyExpenses = db.personalExpenses.filter((pe) => pe.date && pe.date.startsWith(reportMonth));
    const total = monthlyExpenses.reduce((sum, item) => sum + (Number(item.amount) || 0), 0);
    const budget = db.budgets[reportMonth] || 25000;

    DOM.billPaperContent.innerHTML = `
      <div class="bill-header-row">
        <div class="bill-org">
          <h2>अर्थ मन्त्रालय • व्यक्तिगत मासिक खर्च रसिद</h2>
          <p>Official Monthly Personal Statement • ${reportMonth}</p>
        </div>
        <img src="/icons/icon.svg" width="54" height="54" alt="Logo">
      </div>

      <div class="bill-meta-grid">
        <div><strong>महिना (Month):</strong> ${reportMonth}</div>
        <div><strong>प्रयोगकर्ता (User):</strong> ${escapeHtml(db.profile.name)}</div>
        <div><strong>मासिक बजेट सीमा:</strong> ${formatNpr(budget)}</div>
        <div><strong>खर्च भएको जम्मा:</strong> ${formatNpr(total)} (${budget > 0 ? Math.round((total / budget) * 100) : 0}%)</div>
      </div>

      <table class="bill-table" style="margin-top: 14px;">
        <thead>
          <tr>
            <th>मिति</th>
            <th>विधा</th>
            <th>शीर्षक र विवरण</th>
            <th style="text-align: right;">रकम</th>
          </tr>
        </thead>
        <tbody>
          ${monthlyExpenses.map((pe) => `
            <tr>
              <td>${pe.date}</td>
              <td>${escapeHtml(pe.category)}</td>
              <td>
                <strong>${escapeHtml(pe.title)}</strong>
                ${pe.description ? `<br><small style="color: #64748B;">${escapeHtml(pe.description)}</small>` : ""}
              </td>
              <td style="text-align: right; font-weight: 700;">${formatNpr(pe.amount)}</td>
            </tr>
          `).join("")}
        </tbody>
      </table>

      <div class="bill-total-box">
        <div class="bill-total-text">जम्मा मासिक खर्च: ${formatNpr(total)}</div>
      </div>
    `;

    DOM.billViewerModal.classList.remove("hidden");
  }

  function downloadBlob(blob, filename) {
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    setTimeout(() => URL.revokeObjectURL(url), 2000);
  }

  // =========================================================================
  // 13. SETTINGS, BACKUP & RESTORE
  // =========================================================================
  function exportJsonBackup() {
    const backupPayload = {
      profile: db.profile,
      budgets: Object.entries(db.budgets).map(([monthKey, amount]) => ({ monthKey, amount })),
      personalExpenses: db.personalExpenses,
      groupMembers: db.groupMembers,
      groupTransactions: db.groupTransactions
    };

    const str = JSON.stringify(backupPayload, null, 2);
    const blob = new Blob([str], { type: "application/json" });
    const dateStr = new Date().toISOString().split("T")[0];
    downloadBlob(blob, `artha_mantralaya_backup_${dateStr}.json`);
    showToast("JSON ब्याकअप फाइल सुरक्षित भयो (Backup saved)");
  }

  function restoreJsonBackup(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      try {
        const parsed = JSON.parse(e.target.result);
        if (!parsed || !parsed.profile) {
          throw new Error("Invalid schema");
        }

        // Merge or replace
        db.profile = { ...db.profile, ...parsed.profile };
        if (Array.isArray(parsed.budgets)) {
          parsed.budgets.forEach((b) => { db.budgets[b.monthKey] = b.amount; });
        }
        if (Array.isArray(parsed.personalExpenses)) db.personalExpenses = parsed.personalExpenses;
        if (Array.isArray(parsed.groupMembers)) db.groupMembers = parsed.groupMembers;
        if (Array.isArray(parsed.groupTransactions)) db.groupTransactions = parsed.groupTransactions;

        saveDatabase(db);
        populateMonthSelectors();
        applyTheme();
        applyLanguage();
        render();
        showToast("डाटा सफलतापूर्वक पुनःस्थापना भयो! (Restored)");
      } catch (err) {
        alert("ब्याकअप फाइल मिलेन वा बिग्रिएको छ!");
      }
    };
    reader.readAsText(file);
  }

  function resetAllData() {
    promptSecureDelete(() => {
      db = JSON.parse(JSON.stringify(DEFAULT_DB));
      saveDatabase(db);
      populateMonthSelectors();
      applyTheme();
      applyLanguage();
      render();
      showToast("एप नयाँ जस्तै खाली गरियो (Reset successfully)");
    });
  }

  // =========================================================================
  // 14. EVENT BINDINGS
  // =========================================================================
  function setupEventListeners() {
    // Navigation Tabs
    DOM.desktopTabs.forEach((tab) => {
      tab.addEventListener("click", () => switchTab(tab.getAttribute("data-tab")));
    });
    DOM.mobileTabs.forEach((tab) => {
      tab.addEventListener("click", () => switchTab(tab.getAttribute("data-tab")));
    });

    // Brand click -> Home
    DOM.brandBtn.addEventListener("click", () => switchTab("home"));

    // Quick Action & Goto links
    DOM.quickAddPersonalBtn.addEventListener("click", () => {
      window.AppActions.openAddPersonalModal();
    });
    DOM.quickAddGroupBtn.addEventListener("click", () => {
      switchTab("group");
      switchGroupSubTab("transactions");
      window.AppActions.openAddGroupTxModal();
    });
    DOM.gotoGroupBtn.addEventListener("click", () => switchTab("group"));
    DOM.gotoPersonalBtn.addEventListener("click", () => switchTab("personal"));

    // Personal FAB
    DOM.fabAddPersonalBtn.addEventListener("click", () => window.AppActions.openAddPersonalModal());

    // Month Selector
    DOM.activeMonthSelect.addEventListener("change", (e) => {
      state.selectedMonth = e.target.value;
      renderHomeView();
    });
    DOM.reportMonthSelect.addEventListener("change", () => {
      renderReportsView();
    });

    // Edit Budget Trigger
    DOM.editBudgetBtn.addEventListener("click", () => {
      DOM.budgetMonthInput.value = state.selectedMonth;
      DOM.budgetAmountInput.value = db.budgets[state.selectedMonth] || 25000;
      DOM.budgetModal.classList.remove("hidden");
    });

    // User Avatar / Profile Edit
    DOM.homeUserAvatar.addEventListener("click", () => {
      DOM.profileNameInput.value = db.profile.name || "";
      DOM.profileModal.classList.remove("hidden");
    });

    // Personal Search & Filter
    DOM.personalSearchInput.addEventListener("input", (e) => {
      state.personalSearchQuery = e.target.value.trim();
      DOM.clearPersonalSearchBtn.classList.toggle("hidden", !state.personalSearchQuery);
      renderPersonalView();
    });
    DOM.clearPersonalSearchBtn.addEventListener("click", () => {
      DOM.personalSearchInput.value = "";
      state.personalSearchQuery = "";
      DOM.clearPersonalSearchBtn.classList.add("hidden");
      renderPersonalView();
    });

    DOM.personalSortSelect.addEventListener("change", (e) => {
      state.personalSortOrder = e.target.value;
      renderPersonalView();
    });

    DOM.categoryChips.forEach((chip) => {
      chip.addEventListener("click", () => {
        DOM.categoryChips.forEach((c) => c.classList.remove("active"));
        chip.classList.add("active");
        state.personalCategoryFilter = chip.getAttribute("data-cat");
        renderPersonalView();
      });
    });

    // Group Subtabs
    DOM.subtabs.forEach((tab) => {
      tab.addEventListener("click", () => switchGroupSubTab(tab.getAttribute("data-subtab")));
    });

    DOM.openAddMemberBtn.addEventListener("click", () => window.AppActions.openAddMemberModal());
    DOM.openAddGroupTxBtn.addEventListener("click", () => window.AppActions.openAddGroupTxModal());

    DOM.groupSearchInput.addEventListener("input", (e) => {
      state.groupSearchQuery = e.target.value.trim();
      renderGroupView();
    });

    DOM.gtAmount.addEventListener("input", updateGroupSplitCalcPreview);
    DOM.splitMembersCheckboxes.addEventListener("change", updateGroupSplitCalcPreview);

    DOM.selectAllMembersBtn.addEventListener("click", () => {
      DOM.splitMembersCheckboxes.querySelectorAll("input").forEach((cb) => (cb.checked = true));
      updateGroupSplitCalcPreview();
    });
    DOM.deselectAllMembersBtn.addEventListener("click", () => {
      DOM.splitMembersCheckboxes.querySelectorAll("input").forEach((cb) => (cb.checked = false));
      updateGroupSplitCalcPreview();
    });

    // Header Tools
    DOM.langToggleBtn.addEventListener("click", () => {
      db.profile.selectedLanguage = db.profile.selectedLanguage === "ne" ? "en" : "ne";
      saveDatabase(db);
      applyLanguage();
      render();
    });

    DOM.themeToggleBtn.addEventListener("click", () => {
      db.profile.isDarkMode = !db.profile.isDarkMode;
      saveDatabase(db);
      applyTheme();
    });

    DOM.settingsBtn.addEventListener("click", () => {
      DOM.settingsModal.classList.remove("hidden");
    });

    // Settings Modal
    DOM.settingLanguageSelect.addEventListener("change", (e) => {
      db.profile.selectedLanguage = e.target.value;
      saveDatabase(db);
      applyLanguage();
      render();
    });

    DOM.settingThemeSelect.addEventListener("change", (e) => {
      db.profile.isDarkMode = e.target.value === "dark";
      saveDatabase(db);
      applyTheme();
    });

    DOM.settingCalendarSelect.addEventListener("change", (e) => {
      db.profile.calendarMode = e.target.value;
      saveDatabase(db);
      render();
    });

    DOM.setupPinBtn.addEventListener("click", () => {
      const current = db.profile.securityPin;
      const newPin = prompt(
        current
          ? "नयाँ ४-अंकको PIN प्रविष्ट गर्नुहोस् वा निष्क्रिय गर्न खाली छोड्नुहोस् (Enter 4-digit PIN or leave empty to disable):"
          : "नयाँ ४-अंकको सुरक्षा PIN प्रविष्ट गर्नुहोस् (Enter 4-digit PIN):",
        ""
      );
      if (newPin !== null) {
        const trimmed = newPin.trim();
        if (trimmed === "") {
          db.profile.securityPin = "";
          saveDatabase(db);
          setupPinLock();
          showToast("PIN निष्क्रिय गरियो (PIN disabled)");
        } else if (/^\d{4}$/.test(trimmed)) {
          db.profile.securityPin = trimmed;
          saveDatabase(db);
          setupPinLock();
          showToast("सुरक्षा PIN सेट भयो (PIN enabled)");
        } else {
          alert("कृपया ठीक ४ अंक मात्र प्रविष्ट गर्नुहोस् (Must be 4 digits)");
        }
      }
    });

    DOM.exportBackupBtn.addEventListener("click", exportJsonBackup);
    DOM.restoreFileInput.addEventListener("change", (e) => {
      if (e.target.files && e.target.files[0]) {
        restoreJsonBackup(e.target.files[0]);
      }
    });

    DOM.resetAppDataBtn.addEventListener("click", resetAllData);

    // Reports Download Actions
    DOM.downloadPersonalBillBtn.addEventListener("click", downloadMonthlyPersonalBillPdf);
    DOM.openCustomReportDialogBtn.addEventListener("click", () => {
      DOM.crMonthKey.value = state.selectedMonth;
      DOM.customReportModal.classList.remove("hidden");
    });

    DOM.crFilterType.addEventListener("change", (e) => {
      const val = e.target.value;
      DOM.crMonthRow.classList.toggle("hidden", val !== "month");
      DOM.crYearRow.classList.toggle("hidden", val !== "year");
      DOM.crCustomDateRow.classList.toggle("hidden", val !== "custom");
    });

    DOM.printBillBtn.addEventListener("click", () => {
      window.print();
    });

    // Close Modal Buttons
    document.querySelectorAll("[data-close]").forEach((btn) => {
      btn.addEventListener("click", () => {
        const modalId = btn.getAttribute("data-close");
        const modal = document.getElementById(modalId);
        if (modal) modal.classList.add("hidden");
      });
    });

    // Close modals on backdrop click
    document.querySelectorAll(".modal-backdrop").forEach((backdrop) => {
      backdrop.addEventListener("click", (e) => {
        if (e.target === backdrop) {
          backdrop.classList.add("hidden");
        }
      });
    });
  }

  // =========================================================================
  // 15. HELPER UTILITIES
  // =========================================================================
  function showToast(message) {
    const toast = document.createElement("div");
    toast.className = "toast";
    toast.textContent = message;
    DOM.toastContainer.appendChild(toast);
    setTimeout(() => {
      toast.style.opacity = "0";
      toast.style.transition = "opacity 0.3s ease";
      setTimeout(() => toast.remove(), 300);
    }, 2800);
  }

  function escapeHtml(str) {
    if (!str) return "";
    return String(str)
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;")
      .replace(/'/g, "&#039;");
  }

  // Run initial boot
  document.addEventListener("DOMContentLoaded", init);
})();
