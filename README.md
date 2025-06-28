# 📘 Logger Library

A customizable logger library that helps applications log messages to various destinations such as files, databases, or the console.

---

## ✅ Requirements

### 1. Design

- Have to design and implement a logger library that helps applications log messages.
- Client/application uses your logger library to log messages to a sink.

### 2. Message Structure

Each message:

- Has **content** (type: `string`)
- Has a **level** associated with it
- Is directed to a **destination** (sink)
- Has a **namespace** to identify the part of the application that sent the message

### 3. Sink

A sink is the destination for a message:

- Could be a **text file**, **database**, **console**, etc.
- A sink is tied to one or more **message levels**
- One or more message levels can use the same sink

### 4. Logger Library Capabilities

The logger library:

- Accepts messages from one or more clients
- Routes messages to the appropriate sink(s)
- Supports the following message levels:
  - `DEBUG`
  - `INFO`
  - `WARN`
  - `ERROR`
  - `FATAL`
- Enriches each message with the **current timestamp**
- Requires **configuration** during setup

### 5. Sending Messages

- The sink is **not specified** while sending a message to the logger
- The logger uses the **configured level-to-sink mapping**
- Required inputs while sending a message:
  - `message content`
  - `level`
  - `namespace`

---

## ⚙️ Logger Configuration

Specifies all the details required to use the logger library.

### Configuration Fields

- `ts_format`: timestamp format
- `log_level`: minimum logging level
- `sink_type`: type of sink (e.g., FILE, DB)
- Sink-specific details like:
  - `file_location` for FILE sink
  - `dbhost`, `dbport` for DB sink
- Optional:
  - `thread_model`: `SINGLE` or `MULTI`
  - `write_mode`: `SYNC` or `ASYNC`

---

## 📄 Sample Configuration

### Sample Config 1: Text File as Sink

```ini
ts_format: ddmmyyyyhhmmss
log_level: INFO
sink_type: FILE
file_location: /var/log/app/info.log
thread_model: SINGLE        # Optional
write_mode: SYNC            # Optional
