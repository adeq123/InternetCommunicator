package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.NumberFormatter;

import Communication.Server;
import client.ClientViewer.MainPanel;
import client.ClientViewer.SidePanel;
import fittedExceptions.NoUserSelectedException;
import fittedExceptions.UserNumberFieldEmptyException;

public class EditContactListView extends JFrame{
	private boolean add = false;
	private JButton addButton;
	private JButton searchButton;
	private JButton deleteButton;
	private SearchPanel sPanel;
	private ContactListPanel clPanel;
	private JLabel instructionLabel;
	private JFormattedTextField userNoField;
	private JScrollPane listScroller;
	private UserController controller;
	private String foundUserName = null;
	private String tmpSearch = null;
	private JList<String> contactList;

	
	public EditContactListView(int higth, int width, DefaultListModel <String> contactListModel,  UserController controller){
		this.setSize(new Dimension(higth, width));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.controller = controller;
		
		GridBagConstraints c = new GridBagConstraints ();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 10;
		c.weightx = 10;
		
		sPanel = new SearchPanel();
		sPanel.setBorder(BorderFactory.createTitledBorder("Search for new contacts in database and add them to you list..."));
		this.add(sPanel,c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.weightx = 5;
		
		clPanel = new ContactListPanel(contactListModel);
		clPanel.setBorder(BorderFactory.createTitledBorder("Browse or delete from contact list..."));
		
		
		this.add(clPanel,c);
		setVisible(true);
	}
	
	public class SearchPanel extends JPanel{
		
		public SearchPanel(){
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			instructionLabel = new JLabel("Fill in with user number and press search to find the contact...");	
			userNoField = new JFormattedTextField(getNumbersOnlyFormatter());
			userNoField.setToolTipText("Numbers only!");
			searchButton = new JButton("Search");
			searchButton.addActionListener(new searchPressed());
			addButton = new JButton("Add");
			addButton.setEnabled(false);
			addButton.addActionListener(new addPressed());
			
			layout.setHorizontalGroup(
					   layout.createParallelGroup()
					      .addComponent(instructionLabel)
					      .addComponent(userNoField)
					      .addGroup(layout.createSequentialGroup()
					        .addComponent(searchButton)
					           .addComponent(addButton))
					);
			layout.setVerticalGroup(
					   layout.createSequentialGroup()
					           .addComponent(instructionLabel)
					           .addComponent(userNoField,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					   .addGroup(layout.createParallelGroup()
					           .addComponent(searchButton)
					           .addComponent(addButton))
					);
		
		}
		
		
	}

	
	public class ContactListPanel extends JPanel{
		
		private DefaultListModel <String> contactListModel;
		
		public ContactListPanel(DefaultListModel <String> contactListModel){
			
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
			c.ipady = 400;
			contactList.setModel(contactListModel);
			selectFirstItemOnTheList();
			this.add(contactList, c);
			c.ipady = 0;
			c.gridx = 0;
			c.gridy = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			deleteButton = new JButton("Delete");
			deleteButton.addActionListener(new deletePressed());
			
			this.add(deleteButton, c);

		}
		
		public void setContactListModel(DefaultListModel <String> contactListModel){
			this.contactListModel = contactListModel;
			
		}
		
		public void selectFirstItemOnTheList(){
			contactList.setSelectedIndex(0);
		}
	}
	
	
	public void showMsg (String content){
		JOptionPane.showMessageDialog(this,content);
	}
	
	public void setAdd(boolean state){
		add = state;
		addButton.setEnabled(state);
	}
	
	public void setTextFieldEdit(boolean state){
		userNoField.setEditable(state);
	}
	
	public void clearTextField(){
		userNoField.setText("");
	}
	public void setContactListModel(DefaultListModel <String> contactListModel) {
		clPanel.setContactListModel(contactListModel);
		this.repaint();
		
	}

	public void setFoundUserName(String foundUserName){
		this.foundUserName = foundUserName;
	}
	public NumberFormatter getNumbersOnlyFormatter(){
		//sets fromatter
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    return formatter;
	}
	
	public ContactListPanel getCLPanel(){
		return clPanel;
	}
	////////////////////////////////////////////EVENTS///////////////////////////////////////
	public class searchPressed implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(userNoField.getText().equals("")){
				showMsg("Please enter user number if you want to search for it...");
			}else{
				tmpSearch = userNoField.getText();
				try {
					controller.getSendDataModel().sendMessage(Server.SEARCH_BY_NO+" "+userNoField.getText(), null);
					
				} catch (IOException e1) {
				}
			}
			
		}
		
	}

	public class addPressed implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				controller.addNewUser(foundUserName, tmpSearch);
				clPanel.selectFirstItemOnTheList();
				userNoField.setEditable(true);
				userNoField.setText(null);
			} catch (IOException e1) {
				
			}
		}
		
	}

	public class deletePressed implements ActionListener{
		String currentSelectedUser = null;
		public void actionPerformed(ActionEvent e) {
			currentSelectedUser = (String) contactList.getSelectedValue();
			
			if(currentSelectedUser != null){
				controller.deleteUser(contactList.getSelectedValue().split(" ")[1]);
				clPanel.selectFirstItemOnTheList();
			}else{
				try{
					throw new NoUserSelectedException();
				}catch(NoUserSelectedException e2){
					showMsg(e2.getMessage());
				}
			}
		}
		
	}
}
