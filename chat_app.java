import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
class database 
{
	String u_key;							//to store login information
	int i,x;
	String[] username = {"Sahil","Sampada","Pratik","Ravi"};
	int total=username.length-1;
	boolean disp = true;
	String[] password = {"xyz","pqrs","abc","lmn"};
	String message[][][][] = new String[total+1][2][10][2];//4D array number of users 1st row for sent messages and seconnd row for received messages
	//last row is used to store the details of the receiver(0th index) and details of sender(1st index)

	int pos[][] = new int[total+1][2];	//1st for sent 2nd for received

	String get_name(String name)		// in use
	{
		return(name);
	}
	int return_pos(String name,int pos)			//in use
	{
		for(int i=0;i<=total;i++)
		{
			if(name.equals(username[i]))
			{
				pos = i;
			}
		}
		return(pos);
	}										//1st is number of users and 2nd is sent and 3rd is received
	boolean verify_user(String u,String p)		//in use
	{
		boolean flag=false;
		for(i=0;i<=total;i++)
		{
			if(username[i].equals(u) && password[i].equals(p))
			{
				flag = true;
				u_key = get_name(u);				//USER NAME recorded...
				break;
			}
		}
		return(flag);
	}
	String identify_user()
	{
		return(username[i]);
	}
	protected void set_value()			//in use
	{
		for(int i=0;i<total;i++)
		{
			for(int j=0;j<2;j++)
			{
				pos[i][j]=0;
			}
		}
	}
	String store_text(int r,int u,String text)		//in use
	{
		try
		{
			message[u][0][pos[u][0]][0]=text;				//sent message
			message[u][0][pos[u][0]][1]=username[r];
			message[r][1][pos[r][1]][0]=text;				//received message
			message[r][1][pos[r][1]][1]=username[u];
			pos[u][0]++;
			pos[r][1]++;
		}
		catch(NullPointerException ne){}
		catch(ArrayIndexOutOfBoundsException ae)
		{
			for(int i=1;i<=9;i++)
			{
				message[u][0][i-1][0] = message[u][0][i][0];		//sent message shifted
				message[u][0][i-1][1] = message[u][0][i][1];		
				message[r][1][i-1][0] = message[r][1][i][0];		//receiver message shifted
				message[r][1][i-1][1] = message[r][1][i][1];
			}
			pos[u][0]--;
			pos[r][1]--;
		}
		System.out.println("Message information sender: "+u +" "+(pos[u][0]-1)+" "+message[u][0][pos[u][0]-1][0]+" "+message[u][0][pos[u][0]-1][1]);
		System.out.println("Message information receive: "+r +" "+(pos[r][1]-1)+" "+message[r][1][pos[u][0]-1][0]+" "+message[r][1][pos[u][0]-1][1]);
		return(message[u][0][pos[u][0]-1][0]);
	}
}
class chat 
{
	private JFrame user;
	JButton back,send,refresh;
	int pos_r = 0,j=0;
	final JLabel appname;
	JLabel[] m = new JLabel[10];
	int u_no,r_no,pos=0;
	JLabel[] n = new JLabel[10];		//display received messages
	JLabel sent;
	JTextField message_text;
	int i=1;
	String r,temp,temp1,temp2;		//to get user details
	protected chat(String u,String r,database chats)		// u is username and r is receiver name
	{
		temp1 = new String();
		temp2 = new String();
		chats.set_value();
		refresh  =new JButton("Ref");
		refresh.setBounds(320,0,60,25);
		System.out.println("User and Receiver name: "+u+ " "+r);
		u_no = chats.return_pos(u,0);
		r_no = chats.return_pos(r,0);
		System.out.println("user and receiver index "+u_no+" "+r_no);
		this.r = r;
		sent = new JLabel();			//message display label
		user = new JFrame("WEBO");		
		message_text = new JTextField();
		message_text.setBounds(0,320,270,25);
		appname = new JLabel();
		appname.setText(r);
		back = new JButton("<=");
		back.setBounds(0,0,50,25);
		back.addActionListener(new ActionListener()			//BACK Button
		{
			public void actionPerformed(ActionEvent b)
			{
				chats.disp = true;
				user.setVisible(false);
			}
		});
		for(int i=0,j=0;i<10 && j<10;i++,j++)
		{
			m[j] = new JLabel();
			n[i] = new JLabel();
			m[j].setBounds(200,50+30*(j),200,30);
			n[i].setBounds(0,55+30*(i),200,30);
			user.add(m[j]);
			user.add(n[i]);
		}
		appname.setBounds(100,0,100,25); 			//Width == 25
		send = new JButton("Send");
		send.setBounds(280,320,100,25);
		send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent s)		//text not stored in database
			{
				try{
				System.out.println("message typed "+ message_text.getText());
				temp =chats.store_text(r_no,u_no,message_text.getText());
				System.out.println(temp);
				if(pos==10)
				{
					int i=1;
					while(i!=10)			//labels need to be swaped
					{
						temp2 = chats.message[u_no][0][i-1][0];
						temp1 = chats.message[u_no][0][i][0];
						m[i-1].setText(temp2); m[i].setText(temp1);
						i++;
					}
					pos--;
				}
				m[pos].setText(temp);		//message displayed
				pos++;
				message_text.setText(" ");
				}catch(NullPointerException ne){}
			}
		});
		refresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent refresh)
			{
				for(int i=j;i<=chats.pos[r_no][1];i++)				//inside try block
				{
					try{
					System.out.println("xyz "+chats.message[u_no][1][i][1]);
					if(chats.message[u_no][1][i][1].equals(r))
					{
						n[j].setText(chats.message[u_no][1][i][0]);j++;
					}}
					catch(NullPointerException ne){}
				}
			}
		});
		user.add(appname);user.add(back);user.add(refresh);user.add(message_text);user.add(send);
		user.setSize(400,400);
		user.setLayout(null);
		user.setVisible(true);
	}
}
class Home 
{
	int i=1,j=1,k=0;
	String counter = new String();		//to store details of user
	String receiver[]  =new String[4];		// to store details of the current receiver
	private JFrame home;
	final JButton[] contact = new JButton[4];			//Buttons for contacts
	private final JLabel appName;
	int pos[] = new int[4];
	boolean flag=false;
	protected Home(String key,database d)				//key is the contact from database whose account is open
	{
		counter=key;
		home=new JFrame();
		while(j!=5)								//Buttons created
		{
			if(d.username[j-1].equals(counter))
			{
				flag = true;
				j++;
			}
			else
			{
				if(j==1)
				{
					contact[0] = new JButton(d.username[0]);		//JButton declared
					contact[0].setBounds(0,40*i,200,25);
					home.add(contact[0]);							//JButton added
					contact[0].addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent open)		//close previous frames
						{
							System.out.println("User and receiver "+d.username[0]+" "+ key);
							chat ch = new chat(key,d.username[0],d);
						}
								//home.setVisible(!disp);
					});
					j++;i++;
				}
				else if(j==2)
				{
					contact[1] = new JButton(d.username[1]);		//JButton declared
					contact[1].setBounds(0,40*i,200,25);
					home.add(contact[1]);							//JButton added
					contact[1].addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent open)		//close previous frames
						{
							System.out.println(d.username[1]+" "+key);
							chat ch = new chat(key,d.username[1],d);
						}
								//home.setVisible(!disp);
					});
					j++;i++;
				}
				else if(j==3)
				{
					contact[2] = new JButton(d.username[2]);		//JButton declared
					contact[2].setBounds(0,40*i,200,25);
					home.add(contact[2]);							//JButton added
					contact[2].addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent open)		//close previous frames
						{
							System.out.println(d.username[2]+" "+key);
							chat ch = new chat(key,d.username[2],d);
						}
								//home.setVisible(!disp);
					});
					j++;i++;
				}
				else if(j==4)
				{
					contact[3] = new JButton(d.username[3]);		//JButton declared
					contact[3].setBounds(0,40*i,200,25);
					home.add(contact[3]);							//JButton added
					contact[3].addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent open)		//close previous frames
						{
							System.out.println(d.username[3]+" "+key);
							chat ch = new chat(key,d.username[3],d);
						}
								//home.setVisible(!disp);
					 					j++;i++;
				}
			}
		}
		appName = new JLabel("WEBO");
		appName.setBounds(0,0,200,30);
		home.add(appName);
		home.setSize(400,400);
		home.setLayout(null);
		home.setVisible(true);
	}
}
public class login
{
	private static String userName,pass_word,userName2,pass_word2;
	JFrame f;
	String blank = new String();
	boolean flag;
	final JLabel appName,user_label,pass_label;
	JTextField user;
	JPasswordField pass;
	final JButton b;
	JLabel disp;
	protected login(database d)								 
	{
		//1st User Frame...
		f = new JFrame("WEBO");
		appName = new JLabel("WEBO");		//App Name
		appName.setBounds(140,0,120,50);
		user_label = new JLabel("UserName");
		user_label.setBounds(0,100,75,20);		// Username Label
		user = new JTextField();
		user.setBounds(125,100,200,20);			//user name text field
		pass_label = new JLabel("Password");
		pass_label.setBounds(0,150,75,20);		//	Password label
		pass = new JPasswordField();
		pass.setBounds(125,150,200,20);			//password field
		b = new JButton("LOGIN");
		b.setBounds(250,200,100,25);				// Button
		b.addActionListener(new ActionListener()	//Button Action
		{
			public void actionPerformed(ActionEvent e)
			{
				userName = user.getText();						//get username and get password**
				pass_word = new String(pass.getPassword());
				System.out.println("User Details "+userName +" "+pass_word);
				flag = d.verify_user(userName,pass_word);
				if(d.verify_user(userName,pass_word)==true)
				{
					Home h = new Home(userName,d);
					f.setVisible(false);		//close previous window
				}
				else if(d.verify_user(userName,pass_word)!=true)
				{
					JOptionPane.showMessageDialog(f,"Invalid Username or Password");
					user.setText(blank);
					pass.setText(blank);
				}
			}
		});
		
		f.add(appName);f.add(user_label);f.add(pass_label);f.add(user);f.add(pass);f.add(b);
		
		f.setSize(400,400);
		f.setLayout(null);
		f.setVisible(true);
	}
	public static void main(String args[])
	{
		database actual = new database();
		login user1 = new login(actual);
		login user2 = new login(actual);
	}
}