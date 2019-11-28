package com.stocks.stocks.model;

import java.util.List;

public class BinarySearch {
    // Returns index of x if it is present and -1 otherwise
    public static int binarySearch(List<User> userList, String x) {
        int l = 0, r = userList.size() - 1;
        while (l <= r) {
            // find mid
            int m = l + (r - l) / 2;
            int cmpVal = stringCompare(userList.get(m).getCredentials().getUsername(), x);
            // x == middle
            if (cmpVal == 0)
                return m;
            // If x > middle
            if (cmpVal < 0)
                l = m + 1;
            // If x < middle
            else
                r = m - 1;
        }
        // x not found
        return -1;
    }

    // = 0: str1 = str2
    // > 0: str1 > str2
    // < 0: str1 < str2
    public static int stringCompare(String str1, String str2)
    {
        for (int i = 0; i < Math.min(str1.length(), str2.length()); i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        // If one string is nested in the other
        if (str1.length() != str2.length()) {
            return str1.length() - str2.length();
        }
        
        // Strings are equal
        else {
            return 0;
        }
    }
}
