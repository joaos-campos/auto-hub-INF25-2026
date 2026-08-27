/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AutoHubUtil;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LettersOnlyFilterUtil extends DocumentFilter
{
    private final int maxLength;

    public LettersOnlyFilterUtil(int maxLength) 
    {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
            throws BadLocationException 
    {
        replace(fb, offset, 0, text, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)
            throws BadLocationException 
    {
        if (text == null) text = "";

        StringBuilder filtered = new StringBuilder();
        
        for (char c : text.toCharArray()) 
        {
            if (Character.isLetter(c) || c == ' ') filtered.append(c);
        }

        int currentLength = fb.getDocument().getLength();
        
        int room = maxLength - (currentLength - length);
        
        if (room <= 0) return;

        if (filtered.length() > room) 
        {
            filtered.setLength(room);
        }
        super.replace(fb, offset, length, filtered.toString(), attr);
    }
}
