/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AutoHubUtil;

public final class ValidarCpfUtil
{
    private ValidarCpfUtil()
    {
        
    }
    
    public static boolean ValidarCpf(String cpf) 
    {
        if (cpf == null) {
            return false;
        }
        // Remove pontos, traço e os placeholders da máscara — sobram só os dígitos
        cpf = cpf.replaceAll("\\D", "");

        // Precisa ter exatamente 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Rejeita sequências iguais (111.111.111-11 etc.): passam na conta, mas são inválidas
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Primeiro dígito verificador: pesos 10..2 sobre os 9 primeiros dígitos
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int primeiroDV = 11 - (soma % 11);
        if (primeiroDV >= 10) {
            primeiroDV = 0;
        }
        if (primeiroDV != (cpf.charAt(9) - '0')) {
            return false;
        }

        // Segundo dígito verificador: pesos 11..2 sobre os 10 primeiros dígitos
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int segundoDV = 11 - (soma % 11);
        if (segundoDV >= 10) {
            segundoDV = 0;
        }
        return segundoDV == (cpf.charAt(10) - '0');
    }
}
