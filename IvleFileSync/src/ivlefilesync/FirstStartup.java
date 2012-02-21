package ivlefilesync;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Dialog.ModalExclusionType;

public class FirstStartup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FirstStartup thisFrame = null;
	private JPanel contentPane;
	private JTextField txtUserId;
	private JTextField textAPIKey;
	private JTextField textSyncDir;
	private JButton button_1; // Sync Button
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblUserid;
	private JButton btnFinish;
	private JTextArea txtrHelpTextYou;
	private JLabel lblApikey;
	private JTextArea txtrHelpTextEnter;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstStartup frame = FirstStartup.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	public static FirstStartup getInstance() {
		if (thisFrame == null) {
			thisFrame = new FirstStartup();
		}
		return thisFrame;
	}

	public static FirstStartup getRootFrame() {
		return FirstStartup.getInstance();
	}

	/**
	 * Create the frame.
	 */
	private FirstStartup() {
		setResizable(false);
		setSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textSyncDir.setVisible(false);
				button_1.setVisible(false);
				btnPrev.setVisible(false);
				btnFinish.setVisible(false);
				txtrHelpTextEnter.setVisible(false);
			}
		});

		setTitle("First Time Startup");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 555, 387);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(500, 500));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		btnPrev = new JButton("< Previous");
		btnPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				textSyncDir.setVisible(false);
				txtUserId.setVisible(true);
				textAPIKey.setVisible(true);
				lblUserid.setVisible(true);
				lblApikey.setVisible(true);
				button_1.setVisible(false);
				btnPrev.setVisible(false);
				btnFinish.setVisible(true);
				txtrHelpTextEnter.setVisible(false);
				btnNext.setVisible(true);
				txtrHelpTextYou.setVisible(true);
			}
		});
		btnPrev.setName("btnPrevious");
		panel.add(btnPrev);

		btnNext = new JButton("Next >");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textSyncDir.setVisible(true);
				txtUserId.setVisible(false);
				textAPIKey.setVisible(false);
				lblUserid.setVisible(false);
				lblApikey.setVisible(false);
				button_1.setVisible(true);
				btnPrev.setVisible(true);
				btnFinish.setVisible(true);
				txtrHelpTextEnter.setVisible(true);
				btnNext.setVisible(false);
				txtrHelpTextYou.setVisible(false);

			}
		});
		btnNext.setName("btnNext");
		panel.add(btnNext);

		btnFinish = new JButton("Finish");
		btnFinish.addMouseListener(new MouseAdapter() {
			boolean storeInFile = true;

			@Override
			public void mouseClicked(MouseEvent e) {
				// Process the Entries and flag any errors
				String userid = txtUserId.getText();
				String apiKey = textAPIKey.getText();
				String syncDir = textSyncDir.getText();

				// TODO: Check for Input Errors

				if (storeInFile) {
					IVLEOfflineStorage.SetProperty(Constants.UserID, userid);
					IVLEOfflineStorage.SetProperty(Constants.APIKey, apiKey);
					IVLEOfflineStorage.SetProperty(Constants.SyncDirectory,
							syncDir);
				}
				// Attempt Registration
				try {
					// Register
					boolean result = IVLEClientHelper.RegisterDevice(userid,
							apiKey);
					if (result) {
						JOptionPane.showMessageDialog(
								(Component) e.getSource(),
								"Registration Successful");						
						// No need to show it again
						IVLEOfflineStorage.SetProperty(Constants.FIRST_TIME_START, "false");
						
					} else {
						JOptionPane.showMessageDialog(
								(Component) e.getSource(),
								"Registration UnSuccessful! Report Error");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Registration UnSuccessful! Report Error");
					Logger.getLogger(frmSettings.class.getName()).log(
							Level.SEVERE, null, ex);
				}

				IVLELogOutput.getInstance().Log("Exiting the First Time Display");
				// We can safely start thread here
				IVLESystemTray.sync_thread.start();
				// If no input errors then we hide
				FirstStartup.getInstance().dispose();

			}
		});
		panel.add(btnFinish);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JTextArea txtpnWelcomeToThe = new JTextArea();
		txtpnWelcomeToThe.setOpaque(false);
		txtpnWelcomeToThe.setWrapStyleWord(true);
		txtpnWelcomeToThe.setLineWrap(true);
		txtpnWelcomeToThe.setRows(3);
		txtpnWelcomeToThe.setFocusable(false);
		txtpnWelcomeToThe.setEditable(false);
		txtpnWelcomeToThe
				.setText("Welcome to the Desktop Syncing Application for IVLE. In order to get Started Please Enter the information Below");
		panel_1.add(txtpnWelcomeToThe);

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);

		txtUserId = new JTextField();
		txtUserId.setName("txtUserId");
		txtUserId.setText("nusstu\\a00xxxxx");
		txtUserId.setBounds(343, 28, 134, 28);
		layeredPane.add(txtUserId);
		txtUserId.setColumns(10);

		lblUserid = new JLabel("UserId");
		lblUserid.setName("lblUserId");
		lblUserid.setBounds(270, 34, 61, 16);
		layeredPane.add(lblUserid);

		txtrHelpTextYou = new JTextArea();
		txtrHelpTextYou.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtrHelpTextYou.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtrHelpTextYou.setWrapStyleWord(true);
		txtrHelpTextYou
				.setText("Help Text:\nYou need to go to: https://ivlefilesync.sgcloudapp.net\nand need to Enter the API Key");
		txtrHelpTextYou.setRows(3);
		txtrHelpTextYou.setOpaque(false);
		txtrHelpTextYou.setLineWrap(true);
		txtrHelpTextYou.setFocusable(false);
		txtrHelpTextYou.setEditable(false);
		txtrHelpTextYou.setBounds(18, 20, 229, 211);
		layeredPane.add(txtrHelpTextYou);

		lblApikey = new JLabel("APIKey");
		lblApikey.setName("lblAPIKey");
		lblApikey.setBounds(270, 180, 61, 16);
		layeredPane.add(lblApikey);

		textAPIKey = new JTextField();
		textAPIKey.setName("txtAPIKey");
		textAPIKey.setBounds(343, 174, 134, 28);
		layeredPane.add(textAPIKey);
		textAPIKey.setColumns(10);

		textSyncDir = new JTextField();
		textSyncDir.setName("txtSyncDir");
		layeredPane.setLayer(textSyncDir, 1);
		textSyncDir.setBounds(289, 102, 188, 28);
		layeredPane.add(textSyncDir);
		textSyncDir.setColumns(10);

		button_1 = new JButton("...");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				  javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
			        fc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
			        int returnVal = fc.showOpenDialog(null);
			        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            IVLELogOutput.getInstance().Log("Opening: " + file.getName() + "." );
			            textSyncDir.setText(file.getAbsolutePath().toString());
			        }
			}
		});
		button_1.setName("btnSyncDir");
		layeredPane.setLayer(button_1, 1);
		button_1.setBounds(424, 133, 53, 29);
		layeredPane.add(button_1);

		txtrHelpTextEnter = new JTextArea();
		layeredPane.setLayer(txtrHelpTextEnter, 1);
		txtrHelpTextEnter.setWrapStyleWord(true);
		txtrHelpTextEnter
				.setText("Help Text:\nEnter the location of the Sync Folder on your computer");
		txtrHelpTextEnter.setRows(3);
		txtrHelpTextEnter.setOpaque(false);
		txtrHelpTextEnter.setLineWrap(true);
		txtrHelpTextEnter.setFocusable(false);
		txtrHelpTextEnter.setEditable(false);
		txtrHelpTextEnter.setAlignmentY(1.0f);
		txtrHelpTextEnter.setAlignmentX(1.0f);
		txtrHelpTextEnter.setBounds(18, 102, 229, 129);
		layeredPane.add(txtrHelpTextEnter);
	}
}
