package server;

/**
 * Enum identifying each CommPack header
 * @author Jama
 */
public enum CommEnum {

	//LOGIN REQUEST CLIENT->SERVER
	LOGIN,

	//LOGIN REPLY SERVER->CLIENT
	// -- OK
	LOGINSUCCESSFUL,
	// -- NOT OK
	LOGINFAILED,

	//SERVER->CLIENT ERROR MESSAGE: NOT LOGGED IN
	NOTLOGGEDIN,

	//SERVER->CLIENT GENERAL SUCCESS MESSAGE
	SUCCESS,

	//Client->Server GET ROOMS AND DATE
	GETROOMSANDDATE,

	//CLIENT->SERVER GET AN EVENT
	GETEVENT,

	//CLIENT->SERVER GET EVENT BY DAY
	GETEVENTBYDATE,

	//CLIENT->SERVER GET EVENT BY WEEK
	GETEVENTBYWEEK,

	//CLIENT-SERVER ADD AN EVENT
	ADDEVENT,

	//CLIENT->SERVER REMOVE AN EVENT
	REMOVEEVENT, 

	//CLIENT->SERVER GET EVENT LIST
	GETEVENTS,

	//CLIENT->SERVER UPDATE ENTIRE PARTICIPANTLIST
	ADDLISTPARTICIPANT, 

	//CLIENT->SERVER ADD SINGLE PARTICIPANT TO EVENT
	ADDPARTICIPANT, 

	//CLIENT->SERVER REMOVE SINGLE PARTICIPANT FROM EVENT
	REMOVEPARTICIPANT, 

	// SERVER -> CLIENT INVITE NOTIFICATION
	NEWINVITES,

	// SERVER-> CLIENT ALERT NOTIFICATION
	ALERT,

	// SERVER-> CLIENT USER ALREADY IN DB
	USERNAMEALREADYTAKEN, 
	
	// CLIENT -> SERVER EVENT HAS CHANGED, ALERTING AFFECTED USERS
	ALERTEVENTCHANGED, 
	
	// SERVER -> CLIENT ALERTEVENTCHANGED TERMINATED SUCCESSFULLY
	ALERTSUCCESS, 
	
	// SERVER -> CLIENT RECEIVED ALERT
	ALERTRECEIVED, 
	
	// CLIENT -> SERVER GET THE lOGGEDINUSERS LIST FROM SERVER
	GETLATESTUSERS, 
	
	// SERVER -> CLIENT LATEST USERS, HERE THEY ARE
	LATESTUSERSRECEIVED
}
