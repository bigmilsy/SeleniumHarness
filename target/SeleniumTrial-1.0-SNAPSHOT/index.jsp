<%-- 
    Document   : index.jsp
    Created on : 29/12/2019, 9:23:22 AM
    Author     : dmiller
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
        <title>Selenium Webdriver Demo using Chrome</title>
    </head>
    <body style="color:white;background-color: black">
<table align="center" style="font-family:arial;font-size:14pt">
<tr><td align="center">
Dale's Automated Test Harness
</td></tr>
<tr><td align="center">
        <hr /><input type="button" id="btnControl" value="SLOW RUN" onclick="doSlow=true;testCountDown();" />&nbsp;<input type="button" id="btnControl2" value="FAST RUN" onclick="doSlow=false;countMe=2;testCountDown();" />
</td></tr>
<tr><td align="right">
<font id="txtOutput"></font>        
</td></tr>
<tr><td align="center">
<font id="txtOutput2"></font>        
</td></tr>
</table>
<form action="testAJAX.jsp" method="post" id="form1" target="">
<input id="command" name="command" type="hidden" value="" />  
</form>
<div id="form1R" style=""><!-- For server results --></div>        
<script type="text/javascript">
var doSlow = false;      //change to make slow or fast
var outText = '';
var outText2 = '';
var countMe = 5;
var waitSlow = 3500;
var ids = [];
var names = [];
var descriptions = [];
var imgs = [];
var prices = [];
var totalToys = 0;
    
function testCountDown() {
    document.getElementById('btnControl').disabled = 'true';
    document.getElementById('btnControl2').disabled = 'true';
    textCountDownTick();
}
function textCountDownTick() {
    if (countMe > 0) {
      outText = 'Starting tests in ' + countMe;
      document.getElementById('txtOutput').innerHTML = outText;
      countMe--;
      setTimeout('textCountDownTick();', 1000);        
    } else {
      outText = 'START TEST SUITE';
      document.getElementById('txtOutput').innerHTML = outText;
      loadWebdriver();
    }
}
function getDataAndShow() {
  $.post( "entryAJAX.jsp", { command: "getDBData"})
    .done(function( data ) {
      var json = JSON.parse(data);
      outText2 = '<hr />Monitor DB: Toy Names<br /><font style="color:#CCCCCC">';
      for (x = 0 ; x < json.totalRows ; x++) {
          outText2 = outText2 + json.data[x].name + ', ';
      }
      outText2 = outText2 + "</font>";
      document.getElementById('txtOutput2').innerHTML = outText2;
  });    
    setTimeout('getDataAndShow()', 1000);
}
//--------------TEST XX----------------
function loadWebdriver() {
    outText = outText + "</br>";
    outText = outText + "<font style='font-size:125%'>---Pre Execution Checks:</font><br />";
    outText = outText + "Does Selenium Web Driver Load?...";
    printScr();
    //STUB TO STOP TESTS WHEN DEVELOPING
    //return;

  $.post( "entryAJAX.jsp", { command: "loadWebdriverTest"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("checkInitSQL();", waitSlow); }else{checkInitSQL();}
          
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END";
          printScr();
          return;
      }
  });
}
//--------------TEST XX----------------
function checkInitSQL() {
    outText = outText + "Can Test Harness Talk to the DB?...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "checkInitSQL"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("checkInitAPI();", waitSlow); }else{checkInitAPI();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function checkInitAPI() {
    outText = outText + "Can Test Harness Talk to the API?...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "checkInitAPI"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testDBInsert();", waitSlow); }else{testDBInsert();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testDBInsert() {
    outText = outText + "<br /><font style='font-size:125%'>---Regression Tests:</font><br />";
    outText = outText + "TDD: MYSQL TEST: Insert Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testDBInsert"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testDBUpdate();", waitSlow); }else{testDBUpdate();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testDBUpdate() {
    outText = outText + "TDD: MYSQL TEST: Update Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testDBUpdate"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testDBDelete();", waitSlow); }else{testDBDelete();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testDBDelete() {
    outText = outText + "TDD: MYSQL TEST: Delete Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testDBDelete"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testAPICreate();", waitSlow); }else{testAPICreate();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testAPICreate() {
    outText = outText + "TDD: API TEST: Create Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testAPICreate"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testAPISelect();", waitSlow); }else{testAPISelect();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testAPISelect() {
    outText = outText + "TDD: API TEST: Select Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testAPISelect"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testAPIUpdate();", waitSlow); }else{testAPIUpdate();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testAPIUpdate() {
    outText = outText + "TDD: API TEST: Update Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testAPIUpdate"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testAPIDelete();", waitSlow); }else{testAPIDelete();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testAPIDelete() {
    outText = outText + "TDD: API TEST: Delete Toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testAPIDelete"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("checkBodyTestData();", waitSlow); }else{checkBodyTestData();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function checkBodyHeader() {
    outText = outText + "<br /><font style='font-size:125%'>---Regression Tests:</font><br />";
    outText = outText + "WEB TEST: Body Header...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "checkBodyHeader"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("checkBodyTestData();", waitSlow); }else{checkBodyTestData();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function checkBodyTestData() {
    outText = outText + "BDD: WEB CHECK: Page body...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "checkBodyHeader"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testUseWebsite1();", waitSlow); }else{testUseWebsite1();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testUseWebsite1() {
    outText = outText + "BDD: WEB: Create more toys...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testUseWebsite1"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testWebSelect();", waitSlow); }else{testWebSelect();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testWebSelect() {
    outText = outText + "BDD: WEB: Select created toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testWebSelect"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("testWebDelete();", waitSlow); }else{testWebDelete();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function testWebDelete() {
    outText = outText + "BDD: WEB: Delete created toy...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "testWebDelete", name: "Test Bone"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCFFCC'>PASS</font></br>";
          printScr();
//next test
if (doSlow == true){setTimeout("quitWebdriver();", waitSlow); }else{quitWebdriver();}
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END</br>";
          printScr();
          quitWebdriver();
          return;
      }
  });
}
//--------------TEST XX----------------
function quitWebdriver() {
    outText = outText + "<br /><font style='font-size:125%'>---Post Execution Clean:</font><br />";
    outText = outText + "Quit Selenium Web Driver...";
    printScr();

  $.post( "entryAJAX.jsp", { command: "quitWebdriver"})
    .done(function( data ) {
      var json = JSON.parse(data);
      if (json.result == "pass") {
          outText = outText + "<font style='color:#CCCCCC'>OK</font></br>";
          printScr();
      } else {
          outText = outText + "<font style='color:#FFCCCC'>FAIL</font></br>TEST END";
          printScr();
          return;
      }
  });
}
function printScr() {
    document.getElementById('txtOutput').innerHTML = outText;
}
window.onload = function() {
  //testCountDown();
  getDataAndShow();
};    
</script>    
    </body>
</html>
