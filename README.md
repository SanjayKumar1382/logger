Logger Library

Requirements
1. You have to design and implement a logger library that helps applications log messages.
2. Client/application make use of your logger library to log messages to a sink
3. Message
 	a. has content which is of type string
 	b. has a level associated with it
 	c. is directed to a destination (sink)
 	d. has namespace associated with it to identify the part of application that sent the message

4. Sink
	a. This is the destination for a message (eg text file, database, console, etc)
	b. Sink is tied to one or more message level
	c. one or more message level can have the same sink
5. Logger library
	a. Accepts messages from client(s)
	b. Routes messages to appropriate sink
	c. Supports following message level: DEBUG, INFO, WARN, ERROR, FATAL
	d. enriches message with current timestamp while directing message to a sink
	e. requires configuration during setup
6. Sending messages
	a. Sink need not be mentioned while sending a message to the logger library.
	b. A message level is tied to a sink.
	c. You specify message content, level and namespace while sending a message
7. Logger configuration (see sample below)
	a. Specifies all the details required to use the logger library.
	Example:
		i. 	time format
		ii. logging level
		iii.sink type
		iv.	details required for sink (eg file location))
			this depends on sink type

Sample Config1: text file as sink
ts_format:ddmmyyyyhhmmss
log_level:INFO sink_type:FILE
file_location:/var/log/app/info.log
(optional)thread_model:SINGLE
(optional)write_mode:SYNC

Sample Config2: database as sink
ts_format:dd:mm:yyyy hh:mm:ss
log_level:ERROR sink_type:DB
dbhost:<ip address>
dbport:<db port>
(optional)thread_model:MULTI
(optional)write_mode:ASYNC


Sample execution
INFO [2022-06-27 11:14:44,942] Empty txnIds Nothing to fetch
WARN [2022-06-27 11:28:06,229] No user found for the phone number

Log Rotation
application.log application.log.2.gz application.log.1.gz
