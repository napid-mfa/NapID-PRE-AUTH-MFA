package com.napid.bobdemo;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.LeadingMarginSpan;

public class EmailAliasConverter {

    public CharSequence Converter(String inputText){
        String separator = "@";
        int largeTextSize = 18; // Large size in SP
        int smallTextSize = 14; // Small size in SP

// Find the position of '@' symbol
        int atIndex = inputText.indexOf(separator);
        if (atIndex != -1) {
            String beforeText = inputText.substring(0, atIndex);
            String afterText = inputText.substring(atIndex);  // Includes separator

            // Create a SpannableStringBuilder to combine the formatted parts
            SpannableStringBuilder spannableBuilder = new SpannableStringBuilder();

            // Create the part before the separator and set it to a larger size
            SpannableString spannableBefore = new SpannableString(beforeText + "\n"); // Add line break
            spannableBefore.setSpan(
                    new AbsoluteSizeSpan(largeTextSize, true),
                    0,
                    beforeText.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            spannableBuilder.append(spannableBefore);

            // Create the part after the separator, set it to a smaller size, and align it to the right
            SpannableString spannableAfter = new SpannableString(afterText);
            spannableAfter.setSpan(
                    new AbsoluteSizeSpan(smallTextSize, true),
                    0,
                    afterText.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            spannableAfter.setSpan(
                    new LeadingMarginSpan.Standard(100),
                    0,
                    afterText.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            spannableBuilder.append(spannableAfter);

            // Return the formatted text as CharSequence
            return spannableBuilder;
        } else {
            // If no '@' is found, show the text as it is
            return inputText;
        }
    }
}
