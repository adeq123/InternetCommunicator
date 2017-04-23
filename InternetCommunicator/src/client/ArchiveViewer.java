package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import client.EditContactListView.ContactListPanel;
import client.EditContactListView.SearchPanel;
import client.EditContactListView.deletePressed;

public class ArchiveViewer extends JFrame{

	private UserController controller;
	private SelectUserPanel selectUserPanel;
	private ReadArchive archiveRead;
	
	public ArchiveViewer (int higth, int width, DefaultListModel <String> contactListModel,  UserController controller){
		
		this.setSize(new Dimension(higth, width));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.controller = controller;
		
		GridBagConstraints c = new GridBagConstraints ();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 10;
		c.weightx = 1;
		
		selectUserPanel = new SelectUserPanel(contactListModel);
		selectUserPanel.setBorder(BorderFactory.createTitledBorder("Select user which archive you would like to see..."));
		this.add(selectUserPanel,c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.weightx = 10;
		
		archiveRead = new ReadArchive();
		archiveRead.setBorder(BorderFactory.createTitledBorder("Archive view:"));
		
		
		this.add(archiveRead,c);
		setVisible(true);
		
	}
	
	public class SelectUserPanel extends JPanel{
		
		DefaultListModel <String> contactListModel;
		private JList<String> contactList;
		private JButton viewButton;
		private JScrollPane listScroller;
		
		public SelectUserPanel(DefaultListModel <String> contactListModel){
			this.contactListModel = contactListModel;
			this.setLayout(new GridBagLayout());
			this.contactListModel = contactListModel;
			contactList = new JList<String>();
			contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			contactList.setVisibleRowCount(-1);
			
			
			listScroller = new JScrollPane(contactList);
			listScroller.setPreferredSize(new Dimension(250, 80));
			
			GridBagConstraints c = new GridBagConstraints ();
			c.gridx = 0;
			c.gridy = 0;
			c.anchor = GridBagConstraints.NORTH;
			c.ipady = 300;
			c.ipadx = 30;
			c.fill = GridBagConstraints.BOTH;
			contactList.setModel(contactListModel);
			selectFirstItemOnTheList();
			this.add(contactList, c);
			
			GridBagConstraints b = new GridBagConstraints ();
			b.ipady = 0;
			b.gridx = 0;
			b.gridy = 1;
			
			b.anchor = GridBagConstraints.PAGE_END;

			viewButton = new JButton("View");
			viewButton.addActionListener(new viewButtonPressed());
			
			this.add(viewButton, b);
		}

		private void selectFirstItemOnTheList() {
			contactList.setSelectedIndex(0);
			
		}
		
		public String getSelectedUserID(){
			String [] userName = contactList.getSelectedValue().split(" ");
			return userName[userName.length-1];
		}
	}
	
	public class ReadArchive extends JPanel{
		
		private JTextPane readArchivePane;
		private Document doc;
		
		public ReadArchive(){
			this.setLayout(new FlowLayout());
			readArchivePane = new JTextPane();
			readArchivePane.setEditable(false);
			readArchivePane.setBackground(new Color(255, 250, 250));
			doc = readArchivePane.getDocument();
			this.add(readArchivePane);
		}
		
		/**
		 * sets new archive on screen
		 * @param msgContent
		 * @param from
		 * @throws BadLocationException
		 */
		public void showNewArchive(String UID) throws BadLocationException{
			String content;
			try {
				content = controller.getArchive(UID);
				//doc.insertString(0, content+"\n", null);
				readArchivePane.setText(content);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this,"No archive found for this user");
				readArchivePane.setText(null);
				//doc.insertString(0, ""+"\n", null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	///////////////////////EVENTS
	public class viewButtonPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				archiveRead.showNewArchive(selectUserPanel.getSelectedUserID());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			

		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
