package testcase.register;

/**
 * Saare Registration Test Cases — Sheet se liye, duplicates hataye
 *
 * Column order:
 * 0: testCaseName
 * 1: firstName
 * 2: middleName
 * 3: lastName
 * 4: email          ("GENERATE" = auto unique email)
 * 5: password
 * 6: confirmPassword
 * 7: expectedResult  ("PASS" ya "FAIL")
 * 8: termsCheckbox   ("YES" ya "NO")
 * 9: testDescription (console me dikhega — kya test kar rahe hain)
 */
public class TestData {

    public static Object[][] getRegistrationData() {
        return new Object[][] {

            // ═══════════════════════════════════════════════════════════════
            // PASSWORD VALIDATION
            // ═══════════════════════════════════════════════════════════════

            // REG_TC_003 / REG_TC_040 — Password mismatch (duplicate tha, ek rakha)
            {
                "TC_PASS_001_PasswordMismatch",
                "Test", "", "User", "GENERATE",
                "Test@123", "Test@456",
                "FAIL", "YES",
                "Password aur Confirm Password alag hain — error aana chahiye"
            },

            // REG_TC_005 — Short password (sirf 1 char)
            {
                "TC_PASS_002_ShortPassword_1char",
                "Test", "", "User", "GENERATE",
                "a", "a",
                "FAIL", "YES",
                "Bahut chhota password — min 8 chars ka error aana chahiye"
            },

            // REG_TC_043 — Short password A1@ (3 chars)
            {
                "TC_PASS_003_ShortPassword_3char",
                "Test", "", "User", "GENERATE",
                "A1@", "A1@",
                "FAIL", "YES",
                "3 char password — sirf relevant error message aana chahiye"
            },

            // REG_TC_014 — Numeric only password (21 digits)
            {
                "TC_PASS_004_NumericPassword",
                "heena", "", "mansuri", "GENERATE",
                "111111111111111111111", "111111111111111111111",
                "FAIL", "YES",
                "Sirf numbers wala weak password — reject hona chahiye"
            },

            // REG_TC_032 — Valid 8-16 char password (boundary test) — BUG FIXED
            {
                "TC_PASS_005_ValidPassword_8chars",
                "heena", "", "mansuri", "GENERATE",
                "Heee@123", "Heee@123",
                "PASS", "YES",
                "Valid 8 char password — accept hona chahiye (BUG_032 fixed)"
            },

            // REG_TC_048 — Valid password with all types — BUG FIXED
            {
                "TC_PASS_006_ValidPassword_AllTypes",
                "heena", "", "mansuri", "GENERATE",
                "....A1@a", "....A1@a",
                "PASS", "YES",
                "Valid password (upper+lower+number+special) — accept hona chahiye (BUG_047 fixed)"
            },

            // REG_TC_044 — Password with space
            {
                "TC_PASS_007_PasswordWithSpace",
                "heena", "", "mansuri", "GENERATE",
                "Abc 123@", "Abc 123@",
                "PASS", "YES",
                "Password me space — allowed hai (passphrase standard, BUG_043 Not a bug)"
            },

            // TC06 removed — Pass.word@123 actually VALID password hai, site PASS karti hai
            // Agar dot explicitly banned ho to FAIL karo, warna ye test nahi dalna chahiye

            // ═══════════════════════════════════════════════════════════════
            // EMAIL VALIDATION
            // ═══════════════════════════════════════════════════════════════

            // REG_TC_008 — Invalid email format #@12
            {
                "TC_EMAIL_001_InvalidFormat",
                "Test", "", "User", "#@12",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Invalid email #@12 — reject hona chahiye (BUG_008)"
            },

            // REG_TC_049 — Email with only special chars _-+@gmail.com — BUG FIXED
            {
                "TC_EMAIL_002_SpecialCharsOnly",
                "Test", "", "User", "_-+@gmail.com",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Email username sirf special chars — reject hona chahiye (BUG_048 fixed)"
            },

            // REG_TC_047 — Email with space — Not a bug
            {
                "TC_EMAIL_003_EmailWithSpace",
                "heena", "", "mansuri", "1@gmail.com ",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Email me trailing space — reject hona chahiye (BUG_046)"
            },

            // REG_TC_009 — Valid email, valid data — Full success test
            {
                "TC_EMAIL_004_ValidRegistration",
                "heena", "", "mansuri", "GENERATE",
                "Password@123", "Password@123",
                "PASS", "YES",
                "Completely valid data — registration PASS hona chahiye (BUG_009)"
            },

            // REG_TC_065 — Duplicate email — BUG from backend
            {
                "TC_EMAIL_005_DuplicateEmail",
                "heena", "", "mansuri", "testuser_duplicate@mailinator.com",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Already registered email — clear error message aana chahiye (BUG_063)"
            },

            // ═══════════════════════════════════════════════════════════════
            // NAME FIELD VALIDATION
            // ═══════════════════════════════════════════════════════════════

            // REG_TC_010 / REG_TC_041 — Numeric names (duplicate tha, ek rakha) — BUG FIXED
            {
                "TC_NAME_001_NumericNames",
                "1", "2", "3", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Name fields me numbers — reject hona chahiye (BUG_010/040 fixed)"
            },

            // REG_TC_042 — Last Name sirf special chars (..) — BUG FIXED
            {
                "TC_NAME_002_LastNameSpecialCharsOnly",
                "heena", "", "..", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Last name sirf dots — reject hona chahiye (BUG_041 fixed)"
            },

            // REG_TC_050 — Name sirf underscores — BUG FIXED
            {
                "TC_NAME_003_NameUnderscoresOnly",
                "________________", "", "mansuri", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "First name sirf underscores — reject hona chahiye (BUG_049 fixed)"
            },

            // REG_TC_013 — First Name 1407 chars (max limit test)
            {
                "TC_NAME_004_LongName_1407chars",
                "A".repeat(1407), "", "User", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "1407 char name — restrict ya error hona chahiye (BUG_013)"
            },

            // REG_TC_020 — First Name 100 chars
            {
                "TC_NAME_005_LongName_100chars",
                "A".repeat(100), "", "User", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "100 char name — restrict hona chahiye (BUG_020)"
            },

            // REG_TC_045 — Last Name empty (mandatory check) — BUG FIXED
            {
                "TC_NAME_006_LastNameEmpty",
                "heena", "", "", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "YES",
                "Last name khali — mandatory field error aana chahiye (BUG_044 fixed)"
            },

            // REG_TC_011 — Numeric names + dot in password
            {
                "TC_NAME_007_NumericName_DotPassword",
                "1", "2", "3", "GENERATE",
                "Pass.word@123", "Pass.word@123",
                "FAIL", "YES",
                "Numeric names + dot password — validation error aana chahiye (BUG_011)"
            },

            // ═══════════════════════════════════════════════════════════════
            // UI / UX CHECKS (manual verify bhi kar sakte ho)
            // ═══════════════════════════════════════════════════════════════

            // REG_TC_046 — No terms checkbox click
            {
                "TC_UI_001_NoTermsCheckbox",
                "heena", "", "mansuri", "GENERATE",
                "Password@123", "Password@123",
                "FAIL", "NO",
                "Terms checkbox nahi click kiya — submit nahi hona chahiye (BUG_045)"
            },

            // ═══════════════════════════════════════════════════════════════
            // FORM VALIDATION
            // ═══════════════════════════════════════════════════════════════

            // REG_TC_062 — Work Address 181+ chars — BUG FIXED (alag page ka test hai)
            // NOTE: Ye registration form ka nahi, details page ka test hai
            // Selenium me alag test class banana hoga — yahan skip kiya

        };
    }
}