package AcessoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ModuloDbDML 
{

    private final Connection _connection;

    public ModuloDbDML(Connection connection) 
    {
        this._connection = connection;
    }
    
    private void preencherParametros(PreparedStatement ps, Object... parametros) throws SQLException 
    {
        for (int i = 0; i < parametros.length; i++) 
        {
            ps.setObject(i + 1, parametros[i]);
        }
    }

    // ---------- CREATE / UPDATE / DELETE ----------
    // Inserir/atualizar/deletar são a MESMA operação: todas chamam
    // executeUpdate(). Por isso a utilização de um único método central.
    public boolean executar(String sql, Object... parametros) 
    {
        try (PreparedStatement ps = _connection.prepareStatement(sql)) 
        {
            preencherParametros(ps, parametros);
            ps.executeUpdate();
            return true;
        } 
        catch (SQLException erro) 
        {
            System.err.println("Erro ao executar comando: " + erro.getMessage());
            return false;
        }
    }

    // Atalhos para o código de chamada ficar legível (delegam ao executar).
    public boolean inserir(String sql, Object... p)  { return executar(sql, p); }
    public boolean atualizar(String sql, Object... p) { return executar(sql, p); }
    public boolean deletar(String sql, Object... p)   { return executar(sql, p); }

    // ---------- READ (versão recomendada) ----------
    // Lê tudo, fecha os recursos e devolve uma lista pronta. Nada vaza,
    // e quem chama não precisa fechar nada.
    public List<Map<String, Object>> consultar(String sql, Object... parametros) throws SQLException 
    {
        List<Map<String, Object>> linhas = new ArrayList<>();
        
        try (PreparedStatement ps = _connection.prepareStatement(sql)) 
        {
            preencherParametros(ps, parametros);
            
            try (ResultSet rs = ps.executeQuery()) 
            {
                ResultSetMetaData meta = rs.getMetaData();
                int colunas = meta.getColumnCount();
                
                while (rs.next()) 
                {
                    Map<String, Object> linha = new LinkedHashMap<>();
                    
                    for (int i = 1; i <= colunas; i++) 
                    {
                        linha.put(meta.getColumnLabel(i), rs.getObject(i));
                    }
                    linhas.add(linha);
                }
            }
        }
        return linhas; // lista vazia se não houver resultados
    }

    // ---------- READ (devolvendo o ResultSet, como você pediu) ----------
    // ATENÇÃO: quem chama PRECISA fechar o ResultSet (use try-with-resources).
    // Fechar o ResultSet fecha o Statement junto.
    public ResultSet consultarResultSet(String sql, Object... parametros) throws SQLException 
    {
        PreparedStatement ps = _connection.prepareStatement(sql);
        
        try 
        {
            preencherParametros(ps, parametros);
            return ps.executeQuery(); // cursor ANTES da primeira linha
        } 
        catch (SQLException erro) 
        {
            ps.close(); // evita vazar o statement se a query falhar
            throw erro;
        }
    }
    
    public DefaultTableModel consultarTabela(String sql, Object... parametros) throws SQLException 
    {
        DefaultTableModel modelo = new DefaultTableModel() 
        {
            @Override
            public boolean isCellEditable(int linha, int coluna) 
            {
                return false; // tabela apenas para exibição
            }
        };
        
        try (PreparedStatement ps = _connection.prepareStatement(sql)) 
        {
            preencherParametros(ps, parametros);
            
            try (ResultSet rs = ps.executeQuery()) 
            {
                ResultSetMetaData meta = rs.getMetaData();
                int colunas = meta.getColumnCount();

                for (int i = 1; i <= colunas; i++) 
                {
                    modelo.addColumn(meta.getColumnLabel(i)); // cabeçalhos
                }
                
                while (rs.next()) 
                {
                    Object[] linha = new Object[colunas];
                    
                    for (int i = 1; i <= colunas; i++) 
                    {
                        linha[i - 1] = rs.getObject(i);
                    }
                    
                    modelo.addRow(linha);
                }
            }
        }
        return modelo;
    }
    
    public long inserirRetornandoId(String sql, Object... parametros) 
    {
        try (PreparedStatement ps = _connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            preencherParametros(ps, parametros);
            int affectedRows = ps.executeUpdate();
            System.out.println("Executou comando");
            
            if (affectedRows > 0)
            {
                System.out.println("Afetou linhas");
                try (ResultSet chaves = ps.getGeneratedKeys()) 
                {
                    if (chaves.next()) 
                    {
                        System.out.println("Retornou chaves");
                        return chaves.getLong(1); // primeira coluna gerada = o ID
                    }
                }
            }
            System.out.println("Executou mas não retornou chaves");
            return -1; // executou, mas o driver não retornou chave
        }
        catch (SQLException erro) 
        {
            System.err.println("Erro ao inserir: " + erro.getMessage());
            return -1; // -1 sinaliza falha
        }
    }
    
    /*
    public long inserirRetornandoId(String sql, Object... parametros) 
    {
        try (PreparedStatement ps = _connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            preencherParametros(ps, parametros);
            ps.executeUpdate();
            
            try (ResultSet chaves = ps.getGeneratedKeys()) 
            {
                if (chaves.next()) 
                {
                    return chaves.getLong(1); // primeira coluna gerada = o ID
                }
            }
            
            return -1; // executou, mas o driver não retornou chave
        } 
        catch (SQLException erro) 
        {
            System.err.println("Erro ao inserir: " + erro.getMessage());
            return -1; // -1 sinaliza falha
        }
    }
    */
}