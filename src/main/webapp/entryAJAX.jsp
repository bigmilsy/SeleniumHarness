<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.dale.Entry"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
Entry entry = new Entry();
Enumeration en = request.getParameterNames();
HashMap<String, String> pars = new HashMap<String, String>();

// enumerate through the keys and extract the values 
// from the keys! 
while (en.hasMoreElements()) {
    String parameterName = (String) en.nextElement();
    String parameterValue = request.getParameter(parameterName);
    pars.put(parameterName, parameterValue);
}
String command = (String)pars.get("command");    
String result = new String();
if (command.equals("loadWebdriverTest")) {
  result = entry.loadWebdriverTest(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail: Unable to load WebDriver"}<%}
} else if (command.equals("checkBodyHeader")) {
  result = entry.checkBodyHeader(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("checkInitSQL")) {
  result = entry.checkInitSQL(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("checkInitAPI")) {
  result = entry.checkInitAPI(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testDBInsert")) {
  result = entry.testDBInsert(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testDBUpdate")) {
  result = entry.testDBUpdate(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testDBDelete")) {
  result = entry.testDBDelete(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testAPICreate")) {
  result = entry.testAPICreate(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testAPISelect")) {
  result = entry.testAPISelect(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testAPIUpdate")) {
  result = entry.testAPIUpdate(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testAPIUpdate")) {
  result = entry.testAPIUpdate(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testAPIDelete")) {
  result = entry.testAPIDelete(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testUseWebsite1")) {
  result = entry.testUseWebsite1(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testWebSelect")) {
  result = entry.testWebSelect(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("testWebDelete")) {
  result = entry.testWebDelete(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail"}<%}
} else if (command.equals("quitWebdriver")) {
  result = entry.quitWebdriver(pars, request);
  if (result.equals("ok")) {%>{"result": "pass"}<%} else {%>{"result": "fail: Unable to quit WebDriver"}<%}
} else if (command.equals("getDBData")) {
  result = entry.getDBData(pars, request);
  %><%=result%><%
} else {
  %>{"result": "fail: Command '<%=command%>' not found!"}<%
}

//
%>