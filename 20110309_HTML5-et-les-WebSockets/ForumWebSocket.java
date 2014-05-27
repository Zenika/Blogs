package socket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

/**
 * Jetty WebSocketServlet implementation class MarketWebSocket
 */
public class ForumWebSocket extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see WebSocketServlet#WebSocketServlet()
     */
    public ForumWebSocket() {
        super();
        // TODO Auto-generated constructor stub
    }
	private final Set<InternalMarketWebSocket> members = new CopyOnWriteArraySet<InternalMarketWebSocket>();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getNamedDispatcher("default").forward(request, response);
	}
	
	/*
	 * @see org.eclipse.jetty.websocket.WebSocketServlet#doWebSocketConnect(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	protected WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return new InternalMarketWebSocket();
	}
	
	class InternalMarketWebSocket implements WebSocket {
		private Outbound outbound;

		public void onConnect(Outbound outbound) {
			this.outbound = outbound;
			members.add(this);
			for (InternalMarketWebSocket member : members) {
				try {
					member.outbound.sendMessage("CONNECTED:"+members.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/*
		 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, byte[],
		 * int, int)
		 */
		public void onMessage(byte frame, byte[] data, int offset, int length) {
		}

		/*
		 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte,
		 * java.lang.String)
		 */
		public void onMessage(byte frame, String data) {
			
			for (InternalMarketWebSocket member : members) {
				try {
					member.outbound.sendMessage(frame, data);
				} catch (IOException e) {
					// org.eclipse.jetty.util.log.Log.warn(e);
					e.printStackTrace();
				}
			}
		}

		/*
		 * @see org.eclipse.jetty.websocket.WebSocket#onDisconnect()
		 */
		public void onDisconnect() {
			members.remove(this);
			for (InternalMarketWebSocket member : members) {
				try {
					member.outbound.sendMessage("CONNECTED:"+members.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/*
		 * 
		 * @see org.eclipse.jetty.websocket.WebSocket#onFragment(boolean, byte,
		 * byte[], int, int)
		 */
		public void onFragment(boolean more, byte opcode, byte[] data,
				int offset, int length) {

		}
		
	}


}
