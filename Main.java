package Main;
//package Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fasterxml.jackson.core.JsonProcessingException;

import Compare.Executar;
import Compare.FileOps;


import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;



public class Main extends JFrame {
	
	String caminhoArquivosSb2;
	String caminhoLaudo;
	
	private JPanel contentPane;
	private JTextField tfPorcentagem;
	private JTextField tfDirSb2;
	private JTextField tfDirLaudo;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfDirSb2 = new JTextField();
		tfDirSb2.setEditable(false);
		
		tfDirLaudo = new JTextField();
		tfDirLaudo.setEditable(false);
		
		JButton btnDirSb2 = new JButton("Selecionar diret\u00F3rio");
		btnDirSb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();

                // restringe a amostra a diretorios apenas
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int res = fc.showOpenDialog(null);

                if(res == JFileChooser.APPROVE_OPTION){
                   File diretorio = fc.getSelectedFile();
                   caminhoArquivosSb2= diretorio.getAbsolutePath();
                   tfDirSb2.setText(caminhoArquivosSb2);
                }
                
            }
			
			
		});
		btnDirSb2.setBounds(530, 17, 156, 36);
		contentPane.add(btnDirSb2);
		
		JLabel lblPropiedadesASer = new JLabel("Propiedades a ser verificadas");
		lblPropiedadesASer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropiedadesASer.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblPropiedadesASer.setBounds(157, 170, 434, 43);
		contentPane.add(lblPropiedadesASer);
		
		JCheckBox chckbxScript = new JCheckBox("Script");
		chckbxScript.setBounds(320, 220, 97, 23);
		contentPane.add(chckbxScript);
		
		JCheckBox chckbxCostumes = new JCheckBox("Costumes");
		chckbxCostumes.setBounds(320, 246, 97, 23);
		contentPane.add(chckbxCostumes);
		
		JCheckBox chckbxSounds = new JCheckBox("Sounds");
		chckbxSounds.setBounds(320, 272, 97, 23);
		contentPane.add(chckbxSounds);
		
		JCheckBox chckbxObjName = new JCheckBox("objName");
		chckbxObjName.setBounds(438, 220, 97, 23);
		contentPane.add(chckbxObjName);
		
		
		JButton btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				
				FileOps fo = new FileOps();
				
				int porcentagem= Integer.parseInt(tfPorcentagem.getText());
	
				 try {
						fo.extractJsonFromSB2File(caminhoArquivosSb2);
						
						FileOps.compare(caminhoArquivosSb2, caminhoLaudo, porcentagem,
													chckbxSounds.isSelected(), chckbxCostumes.isSelected(),
													chckbxScript.isSelected(),chckbxObjName.isSelected());
						} catch (JsonProcessingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
				
		
			}
		});
		
		btnExecutar.setBounds(286, 422, 156, 43);
		contentPane.add(btnExecutar);
		
		JLabel lblParmetroDePorcentagem = new JLabel("Par\u00E2metro de porcentagem");
		lblParmetroDePorcentagem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblParmetroDePorcentagem.setBounds(0, 112, 219, 36);
		contentPane.add(lblParmetroDePorcentagem);
		
		tfPorcentagem = new JTextField();
		tfPorcentagem.setBounds(219, 112, 109, 37);
		contentPane.add(tfPorcentagem);
		tfPorcentagem.setColumns(10);
		
		JLabel lblDirSb2 = new JLabel("Diret\u00F3rio de arquivos .SB2");
		lblDirSb2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDirSb2.setBounds(0, 17, 219, 36);
		contentPane.add(lblDirSb2);
		
		
		tfDirSb2.setColumns(10);
		tfDirSb2.setBounds(219, 17, 301, 37);
		contentPane.add(tfDirSb2);
		
		
		
		JButton btnDirLaudo = new JButton("Selecionar diret\u00F3rio");
		btnDirLaudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fc = new JFileChooser();

                // restringe a amostra a diretorios apenas
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int res = fc.showOpenDialog(null);

                if(res == JFileChooser.APPROVE_OPTION){
                   File diretorio = fc.getSelectedFile();
                   caminhoLaudo= diretorio.getAbsolutePath();
                   tfDirLaudo.setText(caminhoLaudo);
                }
               
            }
				
				
			
		});
		btnDirLaudo.setBounds(530, 64, 156, 36);
		contentPane.add(btnDirLaudo);
		
		
		tfDirLaudo.setColumns(10);
		tfDirLaudo.setBounds(219, 64, 301, 37);
		contentPane.add(tfDirLaudo);
		
		JLabel lblLaudo = new JLabel("Diret\u00F3rio de sa\u00EDda do laudo");
		lblLaudo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblLaudo.setBounds(0, 64, 219, 36);
		contentPane.add(lblLaudo);
		
		
	}
}
