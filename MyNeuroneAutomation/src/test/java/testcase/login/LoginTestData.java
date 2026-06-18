package testcase.login;

/**
 * Login Module — Saare test cases ka data
 *
 * Column order:
 * 0: testCaseName
 * 1: email
 * 2: password
 * 3: expectedResult   "PASS" ya "FAIL"
 * 4: description      console me dikhega
 */
public class LoginTestData {

    public static Object[][] getLoginData() {
        return new Object[][] {

            // ══════════════════════════════════════════════════════
            // AUTHENTICATION TESTS
            // ══════════════════════════════════════════════════════

            // LOGIN_TC_017 — Valid credentials — BUG pending
            {
                "LOGIN_TC_017_ValidCredentials",
                "testuser@example.com", "Password@123",
                "PASS",
                "Sahi email + password — login hona chahiye (BUG_017 pending)"
            },

            // LOGIN_TC_026 — Login after email verification (different browser simulate)
            {
                "LOGIN_TC_026_AfterEmailVerification",
                "testuser@example.com", "Password@123",
                "PASS",
                "Verified account se login — 'verify email' error nahi aana chahiye (BUG_026)"
            },

            // ══════════════════════════════════════════════════════
            // FORGOT PASSWORD TESTS
            // ══════════════════════════════════════════════════════

            // LOGIN_TC_036 — Forgot Password link click — BUG FIXED
            {
                "LOGIN_TC_036_ForgotPasswordLink",
                "", "",   // email/password nahi chahiye — sirf link click karenge
                "PASS",
                "Forgot Password link — Reset page khulna chahiye, 404 nahi (BUG_035 fixed)"
            },

            // LOGIN_TC_038 — Unregistered email in Forgot Password — BUG FIXED
            {
                "LOGIN_TC_038_ForgotPassword_UnregisteredEmail",
                "test123@abc.com", "",
                "PASS",
                "Unregistered email — generic message aana chahiye, registration status reveal nahi (BUG_037 fixed)"
            },

            // ══════════════════════════════════════════════════════
            // UI / LEGAL LINKS
            // ══════════════════════════════════════════════════════

            // LOGIN_TC_034 — Terms & Privacy links — Already PASS
            {
                "LOGIN_TC_034_TermsAndPrivacyLinks",
                "", "",
                "PASS",
                "Terms & Conditions + Privacy Policy links — sahi page khulna chahiye (Already passing)"
            },

        };
    }
}