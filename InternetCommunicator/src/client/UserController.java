package client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.text.BadLocationException;

import Communication.Message;
import Communication.Server;
import user.User;

public class UserController implements PropertyChangeListener {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private ConnectionModel clientConn;
	private SendDataModel dataSending;
	private ReceiveDataModel receiveMsg;
	public final int DEFAULT_VIEWER_SIZE = 500;
	private ClientViewer clientView;
	private User userModel;
	private String userName = ""; // if application is developed further this one should be taken from registration and stored in DB
	//private Map<String, AbstractModel> modelMap;      // Map that contains models with listeners|
	
	public UserController (int port, String host, String userName) {
		
		clientView = new ClientViewer(DEFAULT_VIEWER_SIZE, DEFAULT_VIEWER_SIZE, this);
		//modelMap = new HashMap<String, AbstractModel>();
		try {
			this.userName = userName;
			this.userModel = new User(userName);
			clientConn = new ConnectionModel(host, port);
			in = clientConn.getInputStream();
			out = clientConn.getOutputStream();
			out.flush();
			dataSending = new SendDataModel(this.out, userModel.ID);
			registerUserOnServer();
			receiveDataThInitialization();
			registerAsListener(receiveMsg);
            System.out.println("Klient startuje na hoscie " +
                    InetAddress.getLocalHost().getHostName());
            clientView.setStatus("Klient startuje na hoscie " + InetAddress.getLocalHost().getHostName());
            loadContactListToView();
            clientView.selectFirstElementOnList();
            clientView.setUserName(userName+" ("+Long.toString(userModel.ID)+")");
		} catch (UnknownHostException e) {
			clientView.showMsg("Wyjatek klienta 1 " + e);
		} catch (IOException e) {
			clientView.showMsg("Wyjatek klienta 1 " + e);
		} 
		
	}
	
	/**
	 * Part of javabeans. Registers as a listener of data received 
	 * @param model
	 */
	public void registerAsListener(AbstractModel model){
		model.addPropertyChangeListener(this);
	}
	/**
	 * Loads users contact list to the viewer
	 */
	public void loadContactListToView(){
		try {
			clientView.clearContactList();
			HashMap <Long, String> contactList = userModel.getContactList().getClReader().getContactList();
			Iterator iter = contactList.entrySet().iterator();
			Map.Entry <Long, String> pair;
			int i =0;
			while(iter.hasNext()){
				System.out.println(i++);
				pair = (Map.Entry <Long, String>)iter.next();
					clientView.addNewUserToContactList(pair.getValue()+" "+pair.getKey());
				
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			clientView.showMsg("Contact list can't be loaded");
			e.printStackTrace();
		}
		
	}
	/**
	 * Starts a new thread for receiving data. 
	 */
	public void receiveDataThInitialization(){
		receiveMsg = new ReceiveDataModel(in, userModel.ID);
		Thread receiveDataProcess = new Thread (receiveMsg);
		receiveDataProcess.start();
	}
	
	
	public SendDataModel getSendDataModel(){
		return dataSending;
	}
	
	public String getArchive(String UID) throws IOException{
		return userModel.getArchive().getArchiveReader().getArchiveContent(UID);
	}
	/**
	 * Sends and archive messages sent
	 * @param content
	 * @param toUser
	 * @throws IOException
	 * @throws BadLocationException 
	 */
	public void sendMessage(String content, String toUser) throws IOException, BadLocationException{
		dataSending.sendMessage(content, toUser);
		userModel.getArchive().getArchiveWriter().addNewTalk(toUser,"Messeage sent to user" +toUser + System.lineSeparator() + content);
		clientView.addNewMesseage(content, "sent to user: "+toUser);
		
	}
	/**
	 * Registers new user on the server according to the servers' protocol
	 * @throws IOException
	 */
	private void registerUserOnServer() throws IOException {
		dataSending.sendMessage(Server.REGISTER+" "+userName, Long.toString(userModel.ID));
	}
	
	public void exitChat() throws IOException{
		dataSending.sendMessage(Server.EXIT, null);
		receiveMsg.exitThread();
		clientConn.closeConnection();
		
	}
	
	
	/**
	 * Method that is executed when Event evt occurs and that is when firePropertyChange is triggered.
	 * @param evt
	 */
	public void propertyChange(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		Message newMsg = null;

		if(propertyName.equals(ReceiveDataModel.MSG_RECEIVED)){
			newMsg = (Message) evt.getNewValue();
			if(newMsg.getFromUser().equals(Server.FROM_SERVER)){
				if(newMsg.getContent().equals(Server.NO_USER_FOUND)){
					clientView.getCLView().showMsg("No user found!");
				}else{
					clientView.getCLView().showMsg("User was found in the database! You can add him to db");
					clientView.getCLView().setAdd(true);
					clientView.getCLView().setTextFieldEdit(false);
					clientView.getCLView().setFoundUserName(newMsg.getContent().split(" ")[1]);
				}
			}else{
				try {
					clientView.addNewMesseage(newMsg.getContent(), "received from user: "+newMsg.getFromUser());
					try {
						userModel.getArchive().getArchiveWriter().addNewTalk(newMsg.getFromUser(), "New message received from user"+newMsg.getFromUser()+
								System.lineSeparator()+newMsg.getContent());
					} catch (IOException e) {
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addNewUser(String name, String ID) throws IOException{
		userModel.getContactList().getClWriter().addContact(name, ID);
		loadContactListToView();
	}
	public static void main(String[] args) throws InterruptedException {
		
		String s;
		try {
			s = InetAddress.getLocalHost().getHostName();
			UserController user1 = new UserController(7778, s,"UserA");
			System.out.println("Client 1 started!");
			UserController user2 = new UserController(7778, s, "UserB");
			System.out.println("Client 2 started!");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public void deleteUser(String ID) {
		try {
			userModel.getContactList().getClWriter().deleteContact(ID);
			loadContactListToView();
		} catch (IOException e) {

		}
		
	}
}