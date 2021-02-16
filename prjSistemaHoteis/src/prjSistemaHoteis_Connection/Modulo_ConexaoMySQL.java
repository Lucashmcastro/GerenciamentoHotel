package prjSistemaHoteis_Connection;
import java.sql.*;
import javax.swing.JOptionPane;

public class Modulo_ConexaoMySQL {
    
    public static Connection Conexao(){ //Método responsável por realizar a conexão com o banco de dados MySQL.
        java.sql.Connection conexao = null;
        
        String driver = "com.mysql.jdbc.Driver"; //Chama o driver importado na biblioteca.
        
        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "root";
        String password = "";
                                                //Armazena informações necessárias para conexão com o banco de dados.
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            
            return conexao;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
}
