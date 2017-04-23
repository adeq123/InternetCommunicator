package client;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import fittedExceptions.NoUserSelectedException;

public class ClientViewer extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton sendButton;
	private JTextPane readMsg;
	private JEditorPane writeMsg;
	private MainPanel comPanel;
	private UserController controller;
	private JList<String> contactList;
	private DefaultListModel <String> contactListModel; 
	private SidePanel sidePanel;
	private JScrollPane listScroller;
	private JLabel statusLabel;
	private Document doc;
	private JLabel userNameLabel;
	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem contact;
	private JMenuItem viewArchive;
	private EditContactListView ConListView = null;
	private ArchiveViewer archiveViewer;
	public final static int CL_WINDOW_SIZE = 500;
	
 	public ClientViewer(int high, int width, UserController controller){

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, high);
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints ();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 10;
		c.weightx = 10;
		
		comPanel = new MainPanel();
		comPanel.setBorder(BorderFactory.createTitledBorder("Super communicator"));
		this.add(comPanel,c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.weightx = 5;
		
		sidePanel = new SidePanel();
		sidePanel.setBorder(BorderFactory.createTitledBorder("ContactList"));
		
		
		this.add(sidePanel,c);
		setVisible(true);
		this.controller = controller;
		
		menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		file = new JMenu("File");
		menubar.add(file);
		
		
		exit = new JMenuItem("Exit");
		viewArchive = new JMenuItem("View Archive");
		contact = new JMenuItem("Contacts..");
		file.add(exit);
		file.add(contact);
		file.add(viewArchive);
		
		ExitPressed e = new ExitPressed();
		exit.addActionListener(e);
		
		ContactPressed b = new ContactPressed();
		contact.addActionListener(b);
		
		ViewPressed v = new ViewPressed();
		viewArchive.addActionListener(v);
		
	}
	
	public class MainPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MainPanel(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints ();
			
			userNameLabel = new JLabel("");
			c.ipady = 1; //height of this pane
			c.gridx = 0;
			c.gridy = 0;
			c.weighty = 0.1;
			c.weightx = 0.1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			this.add(userNameLabel, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.fill = GridBagConstraints.BOTH;
			c.weighty = 10;
			c.weightx = 10;
			readMsg = new JTextPane();
			readMsg.setEditable(false);
			readMsg.setBackground(new Color(255, 250, 250));
			doc = readMsg.getDocument();
			this.add(readMsg, c);
			
	
			c.gridx = 0;
			c.gridy = 3;
			writeMsg = new JEditorPane();
			this.add(writeMsg, c);
			
			c.ipady = 1; //height of this pane
			c.gridx = 0;
			c.gridy = 2;
			c.weighty = 0.1;
			c.weightx = 0.1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.EAST;
			sendButton = new JButton("Send");
			sendButton.addActionListener(new sendButtonPressed());
			this.add(sendButton, c);
			
			c.anchor = GridBagConstraints.WEST;
			statusLabel = new JLabel("Status....");
			this.add(statusLabel, c);
			
		}
	}
	public class SidePanel extends JPanel{
		
		public SidePanel(){
			contactListModel = new DefaultListModel<String>();
			setLayout(new FlowLayout());
			contactList = new JList<String>();
			contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			contactList.setVisibleRowCount(-1);
			
			listScroller = new JScrollPane(contactList);
			listScroller.setPreferredSize(new Dimension(250, 80));
			
			contactList.setModel(contactListModel);
			this.add(contactList);
			
		}
		
		
	}
	/******************************METHODS******************/
	/**
	 * Adds new messeage on screen
	 * @param msgContent
	 * @param from
	 * @throws BadLocationException
	 */
	public void addNewMesseage(String msgContent, String from) throws BadLocationException{
		doc.insertString(0, msgContent+"\n", null);
		doc.insertString(0, "Messeage "+from+"\n", null);
	}
	
	/**
	 * adds new user to the contact list 
	 * @param newUser
	 */
	public void addNewUserToContactList(String newUser){
		
		contactListModel.addElement(newUser);
		contactList.setModel(contactListModel);
		//sidePanel.add(contactList);
		sidePanel.repaint();
		if(ConListView != null)
			ConListView.getCLPanel().repaint();
		
	}
	
	/**
	 * clears content of the contact list
	 */
	public void clearContactList(){
		
		contactListModel.clear();;		
	}
	
	/**
	 * removes single user from the contact list
	 * @param deleteUser
	 */
	public void deleteUserFromContactList(String deleteUser){
		
		contactListModel.removeElement(deleteUser);
	}
	
	/**
	 * Shows a dialog messeage on the screen
	 * @param content
	 */
	public void showMsg (String content){
		JOptionPane.showMessageDialog(comPanel,content);
	}
	
	/**
	 * Sets a text to the lable located between two panels.
	 * @param status
	 */
	public void setStatus (String status){
		this.statusLabel.setText(status);
	}
	
	/**
	 * Sets user name on the viewer
	 * @param userName
	 */
	public void setUserName(String userName){
		userNameLabel.setText(userName);
	}
	public static void main(String args[]){
		ClientViewer v = new ClientViewer (500,500,null);
	}

	/***************************EVENTS**********************/
	public class sendButtonPressed implements ActionListener{
		String newMsg = null;
		String currentSelectedUser = null;
		String writeTo;
		String [] currentSelectedUserMat;
		
		public void actionPerformed(ActionEvent e) {
			
			newMsg = writeMsg.getText();
			currentSelectedUser = (String) contactList.getSelectedValue();

			
			if(currentSelectedUser == null){
					try {
						throw new NoUserSelectedException();
					} catch (NoUserSelectedException e1) {
						showMsg(e1.getMessage());
					}
			}else {
				currentSelectedUserMat = currentSelectedUser.split(" ");
				writeTo = currentSelectedUserMat[currentSelectedUserMat.length-1];
				
				if(newMsg.equals("")){
					
				}else{
					try {
						controller.sendMessage(newMsg, writeTo);
					} catch (IOException | BadLocationException e1) {
						
					} 
				}
			}
			clearWriteText();

		}
		

	 }
	
	public class ExitPressed implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {
			try {
				controller.exitChat();
				System.exit(0);
			} catch (IOException e1) {
				showMsg("Program did not exit properly...");
			}
			
			
		}
	 
	}
	
	public class ContactPressed  implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ConListView = new EditContactListView(CL_WINDOW_SIZE , CL_WINDOW_SIZE, contactListModel, controller );
			
		}
		
	}
	
	public class ViewPressed  implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			archiveViewer = new ArchiveViewer(CL_WINDOW_SIZE , CL_WINDOW_SIZE, contactListModel, controller );
			
		}
		
	}
/***************************GETTERS**********************/
	public JTextPane getReadMsgPane(){
		return readMsg;
	}
	
	public JEditorPane getWriteMsgPane(){
		return writeMsg;
	}
	
	public JButton getSendButton(){
		return sendButton;
	}
	
	public JPanel getCommunicationPanle(){
		return comPanel;
	}
	
	public JPanel getSidePanel(){
		return sidePanel;
	}
	
	public EditContactListView getCLView(){
		return ConListView;
		
	}
	
	public void selectFirstElementOnList(){
		contactList.setSelectedIndex(0);
	}
	
	public void clearWriteText(){
		writeMsg.setText("");	
		}
}
