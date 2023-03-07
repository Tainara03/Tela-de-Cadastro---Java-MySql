package br.com.IT.telas;

import java.sql.*;
import br.com.IT.dal.ModuloConexao;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class tlLogin extends JFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JLabel txtStatus;
	private JButton btnRegister;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tlLogin frame = new tlLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void logar() {
		String sql="SELECT * FROM tb_login WHERE username=? AND user_password=?";
		//consulta o banco. O ? é substituído pelo conteúdo da variável
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUser.getText());
			String savePass = new String(txtPassword.getPassword());
			//pst.setString(2, txtPassword.getText());
			pst.setString(2, savePass);
			//executa a query
			rs = pst.executeQuery();
			//se existe usuario e senha correspondente
			if (rs.next()) {
				tlPrincipal principal = new tlPrincipal();
				principal.setVisible(true);
				//fecha a tela de login, quando abrir a tela principal
				this.dispose();
				//interrompe a conexao com o banco de dados
				conexao.close();
			}else {
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido(s).");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public void cadastrar() {
		String sql="INSERT INTO tb_login(username, user_password) VALUES(?, ?)";
		//insere no banco. O ? é substituído pelo conteúdo da variável
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUser.getText());
			String savePass = new String(txtPassword.getPassword());
			pst.setString(2, savePass);
			//executa a query
			pst.execute();			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Create the frame.
	 */
	public tlLogin() {
		conexao = ModuloConexao.conector();
		System.out.println(conexao);
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 336, 177);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USUÁRIO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(36, 33, 72, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("SENHA");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(46, 62, 72, 18);
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ao clicar em "Login" irá chamar a função criada Logar
				logar();
			}
		});
		btnLogin.setBounds(56, 106, 100, 23);
		contentPane.add(btnLogin);
		
		txtUser = new JTextField();
		txtUser.setBounds(116, 29, 145, 23);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(116, 58, 145, 23);
		contentPane.add(txtPassword);
		
		txtStatus = new JLabel("New label");
		txtStatus.setFont(new Font("Tahoma", Font.BOLD, 9));
		if (conexao != null) {
			txtStatus.setText("Conectado");
			txtStatus.setForeground(Color.GREEN);
		} else {
			txtStatus.setText("Não Conectado");
			txtStatus.setForeground(Color.RED);
		}
		
		txtStatus.setBounds(223, 4, 89, 14);
		contentPane.add(txtStatus);
		
		btnRegister = new JButton("Cadastrar");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ao clicar em "Cadastrar" irá chamar a função criada Cadastrar
				cadastrar();
			}
		});
		btnRegister.setBounds(173, 106, 100, 23);
		contentPane.add(btnRegister);
		
	}
}
