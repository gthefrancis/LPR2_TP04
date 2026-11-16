// Guilherme Bertero CB3037126
// Guilherme Francisco CB3037461

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Font;

public class TrabalhoPratico04 extends JFrame {

    // 1. VARIÁVEIS GLOBAIS DA CLASSE
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    private JTextField txtNomeBusca;
    private JTextField txtNome;
    private JTextField txtSalario;
    private JTextField txtCargo;

    // 2. CONSTRUTOR
    public TrabalhoPratico04() {
        setTitle("TRABALHO PRATICO 04");
        setSize(360, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 

        // ================= PARTE SUPERIOR (Busca) =================
        JLabel lblNomeBusca = new JLabel("Nome:");
        lblNomeBusca.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNomeBusca.setBounds(30, 20, 50, 20);
        add(lblNomeBusca);

        txtNomeBusca = new JTextField("marcelo");
        txtNomeBusca.setBounds(80, 20, 240, 20);
        add(txtNomeBusca);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(130, 50, 100, 25);
        add(btnPesquisar);

        // ================= PARTE CENTRAL (Dados) =================
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNome.setBounds(10, 100, 80, 20);
        add(lblNome);

        txtNome = new JTextField("Marcelo");
        txtNome.setBounds(170, 100, 150, 20);
        add(txtNome);

        JLabel lblSalario = new JLabel("Salário:");
        lblSalario.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSalario.setBounds(10, 125, 80, 20);
        add(lblSalario);

        txtSalario = new JTextField("2000.00");
        txtSalario.setBounds(170, 125, 150, 20);
        add(txtSalario);

        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblCargo.setBounds(10, 150, 80, 20);
        add(lblCargo);

        txtCargo = new JTextField("Administrativo");
        txtCargo.setBounds(170, 150, 150, 20);
        add(txtCargo);

        // ================= PARTE INFERIOR (Navegação) =================
        JButton btnAnterior = new JButton("Anterior");
        btnAnterior.setBounds(10, 185, 160, 25);
        add(btnAnterior);

        JButton btnProximo = new JButton("Próximo");
        btnProximo.setBounds(175, 185, 145, 25);
        add(btnProximo);

        // ================= AÇÕES DOS BOTÕES =================
        
        btnPesquisar.addActionListener(e -> {
            
            System.out.println("--> BOTAO CLICADO! Iniciando acao..."); 
            
            try {

                if (conn == null || conn.isClosed()) {
                    // 2. Rastreador de conexão
                    System.out.println("--> Tentando conectar ao banco de dados..."); 
                    
                    String url = "jdbc:sqlserver://localhost:1433;databaseName=aulajava;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
                    conn = DriverManager.getConnection(url);
                    
                    // 3. Rastreador de sucesso
                    System.out.println("--> Conexao estabelecida com sucesso!"); 
                }

                if (conn == null || conn.isClosed()) {
                    String url = "jdbc:sqlserver://localhost:1433;databaseName=aulajava;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
                    conn = DriverManager.getConnection(url);
                }

                String sql = "SELECT f.nome_func, f.sal_func, c.ds_cargo " +
                             "FROM tbfuncs f " +
                             "INNER JOIN tbcargos c ON f.cod_cargo = c.cd_cargo " +
                             "WHERE f.nome_func LIKE ?";

                stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, "%" + txtNomeBusca.getText() + "%");
                rs = stmt.executeQuery();

                if (rs.first()) {
                    preencherCampos();
                } else {
                    limparCampos();
                    JOptionPane.showMessageDialog(this, "Nenhum funcionário encontrado com esse nome!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro no Banco de Dados:\n" + ex.getMessage());
            }
        });

        btnProximo.addActionListener(e -> {
            try {
                if (rs != null) {
                    if (rs.next()) {
                        preencherCampos();
                    } else {
                        rs.last();
                        JOptionPane.showMessageDialog(this, "Você já está no último registro!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnAnterior.addActionListener(e -> {
            try {
                if (rs != null) {
                    if (rs.previous()) {
                        preencherCampos();
                    } else {
                        rs.first();
                        JOptionPane.showMessageDialog(this, "Você já está no primeiro registro!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        setLocationRelativeTo(null);
    }

    // 3. MÉTODOS AUXILIARES
    
    private void preencherCampos() throws SQLException {
        txtNome.setText(rs.getString("nome_func").trim());
        txtSalario.setText(String.valueOf(rs.getBigDecimal("sal_func")));
        txtCargo.setText(rs.getString("ds_cargo").trim());
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSalario.setText("");
        txtCargo.setText("");
    }

    // 4. MAIN (Porta de entrada)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrabalhoPratico04 tela = new TrabalhoPratico04();
            tela.setVisible(true);
        });
    }
}
