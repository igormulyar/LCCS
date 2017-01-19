document.addEventListener('DOMContentLoaded', function () {

    var EMPTY = 'no info';

    var Law = function() {
    var LINK = 'http://localhost:8080',
        allInfoArray;

    this.getAllInfoArray = function(){
      return allInfoArray;
    }

    this.getAmountOfCases = function(){
      return allInfoArray.length;
    }

    this.addNumber = function(number) {
      // POST body:{number}	/tracked_numbers/
      // Добавить номер дела для отслеживания
      // Этот запрос на добавление переданного пользователем номера дела к номерам для отслеживания.
      // Делается POST запрос к ресурсу tracked_numbers, тело запроса должно содержать номер дела, который мы добавляем - ""number""
      var xhr = new XMLHttpRequest(),
          url = LINK + "/tracked_numbers/",
          params = number;
      xhr.open("POST", url, false);
      xhr.send(params);
    }

    this.deleteNumber = function(id) {
      // DELETE /tracked_numbers/number
      // Удалить номер дела, которое нужно отслеживать
      // в url- е укзывается номер дела, которое мы больше не хотим отвлеживать (number)
      var xhr = new XMLHttpRequest(),
          url = LINK + "/tracked_numbers/" + id,
          params = '';
      xhr.open("DELETE", url, true);
      xhr.send(params);
    }

    this.getCases = function() {
      // GET /hearigs/
      // get all cases from server
      // Показать список номеров дел, которые отслеживаются
      allInfoArray = [];
      var xhr = new XMLHttpRequest(),
          url = LINK + "/hearings/",
          params = '';
      xhr.open("GET", url, false);
      xhr.onreadystatechange = function() {
        if (this.readyState != 4) return;
        allInfoArray = JSON.parse(xhr.responseText);
        console.log(allInfoArray);
      };
      xhr.send(params);
    }

    this.clearXss = function(text){
      var text = text || "",
          lt = /</g,
          gt = />/g,
          ap = /'/g,
          ic = /"/g;
      if (text!=null && text!=undefined && text!="undefined" && text!=""){
        return text.toString().replace(lt, "&lt;").replace(gt, "&gt;").replace(ap, "&#39;").replace(ic, "&#34;");
      }
      else {
        return EMPTY;
      }
    }

    this.fillTable = function(num) {

      if (!allInfoArray[num]){
        allInfoArray[num] = {
          date: EMPTY,
          involved: EMPTY,
          description: EMPTY,
          judge: EMPTY,
          form: EMPTY,
          address: EMPTY,
          courtName: EMPTY
        };
      }
      var buffer = {
          number: this.clearXss(allInfoArray[num].number.number) || 0,
          id: this.clearXss(allInfoArray[num].number.id) || 0,
          date: this.clearXss(allInfoArray[num].date) || EMPTY,
          involved: this.clearXss(allInfoArray[num].involved) || EMPTY,
          description: this.clearXss(allInfoArray[num].description) || EMPTY,
          judge: this.clearXss(allInfoArray[num].judge) || EMPTY,
          form: this.clearXss(allInfoArray[num].form) || EMPTY,
          address: this.clearXss(allInfoArray[num].address) || EMPTY,
          courtName: this.clearXss(allInfoArray[num].courtName) || EMPTY
         };
      table.addRow(buffer);
    }


  };

  var law = new Law;

  // WORK WITH TABLE
  var delBtn = document.getElementsByClassName('delete-btn');

  var Table = function() {
    var TABLE_ID = 'tbody';

    this.getTimeMarker = function(date) {
      var date = date.split(' ')[0] || '29.19.2999',
          result,
          now = new Date(),
          today = new Date(now.getFullYear(), now.getMonth(), now.getDate()).valueOf();

      //if Year is missing
      if ( (date.split('.')[2]=="")  ) {
        date = date + "" + now.getFullYear();
      } else if ( (date.split('.')[2]==undefined) ) {
        date = date + "." + now.getFullYear();
      }

      var caseDate = new Date(date.split('.')[2], date.split('.')[1]-1, date.split('.')[0]).valueOf();

      if (today == caseDate){
        result = 'fa-exclamation-circle priority-today';
      } else if ( ((caseDate-today)/24/60/60/1000) == 1 ) {
        result = 'fa-circle priority-tomorrow';
      } else if (  ( ((caseDate-today)/24/60/60/1000) <= 7 ) && ( ((caseDate-today)/24/60/60/1000) > 0 )  ){
        result = 'fa-circle priority-week';
      } else {
        result = 'fa-circle priority-none';
      }

      return result;
    }

    this.breakDate = function(date) {
      var date = date || '- -';
      if (date!=EMPTY){
        return date.split(' ')[0] + ' &nbsp; &nbsp; &nbsp; ' + date.split(' ')[1];
      } else {
        return date;
      }
    }

    this.convertDateToGoogleFormat = function(date) {
      var date = date || '01.01.1999 10:00';
      if (!date || date == EMPTY) {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1;
        var yyyy = today.getFullYear();
        if(dd < 10) {
          dd = '0' + dd
        }
        if(mm < 10) {
          mm = '0' + mm
        }
        date = dd + '.' + mm + '.' + yyyy + ' 08:00';
      } else {
        date = date;
      }
      var result,
          timeForSession = 2,
          onlyDate = date.split(' ')[0],
          time =  date.split(' ')[1];
      result = "" + onlyDate.split('.')[2] + onlyDate.split('.')[1] + onlyDate.split('.')[0] +
               "T" + time.split(':')[0] + time.split(':')[1] + "00/" +
               onlyDate.split('.')[2] + onlyDate.split('.')[1] + onlyDate.split('.')[0] +
               "T" + (parseInt(time.split(':')[0]) + timeForSession) + time.split(':')[1] + "00";
      return result;
    }


    this.createGoogleLink = function(obj){
      var obj = obj || {number: 'n', date: '01.01.1999 10:00', involved: 'i', description: 'd', judge: 'j', form: 'f', address: 'a', courtName: 'c' };
      var result = '#';

      result = 'https://calendar.google.com/calendar/render?action=TEMPLATE' +
               '&text=' + encodeURIComponent(obj.number) +
               '&location=' + encodeURIComponent(obj.address + ', ' + obj.courtName) +
               '&dates=' + encodeURIComponent(this.convertDateToGoogleFormat(obj.date)) +
               '&details=' + encodeURIComponent(obj.form + ': ' +  obj.description + '.\n' + obj.involved + '.\nСуддя: ' + obj.judge ) +
               '&trp=false&pli=1&sf=true&output=xml';

      return (result);
    }

    this.createReyestrLink = function(num){
      //TODO: rebuild this function
      var num = num || "none",
          result = '#';
      result = 'http://reyestr.court.gov.ua/?CaseNumber=' + encodeURIComponent(num) + '&PagingInfo.ItemsPerPage=25&Sort=1';
      return result;
    }

    this.addRow = function(obj) {
      obj = obj || {number: 'empty'};
      var tr = document.createElement('tr');
      tr.innerHTML = '<td data-javaid="' + obj.id + '"><span class="delete-btn tooltip grey-tooltip mini-tooltip" data-help="Delete case from list"><i class="fa fa-trash-o" aria-hidden="true"></i></span> ' +
                      '<a class="tooltip grey-tooltip mini-tooltip" data-help="Look at additional files" href="' + this.createReyestrLink(obj.number) + '" target=_blank>' +  obj.number + '</a></td>' +
                      '<td><a href="' + this.createGoogleLink(obj) + '" data-help="Add to Google calendar" class="google-link tooltip grey-tooltip mini-tooltip" target="_blank"><i class="fa indicator ' +
                      this.getTimeMarker(obj.date) + '" aria-hidden="true"></i></a> ' + this.breakDate(obj.date) + '</td>' +
                      '<td>' + obj.involved + '</td>' +
                      '<td>' + obj.description + '</td>' +
                      '<td>' + obj.judge + '</td>' +
                      '<td>' + obj.form + '</td>' +
                      '<td><span class="tooltip grey-tooltip" data-help="' + obj.address + '">' + obj.courtName + '</span></td>' ;
      document.getElementById(TABLE_ID).appendChild(tr);
      getByClassName(delBtn);
      return false;
    }

    this.deleteAllRaws = function() {
      for (i = delBtn.length-1; i >= 0; i--) {
        delBtn[i].parentNode.parentNode.remove();
      }
    }

  };

  var caseObject = {
    number: '0',
    id: '0',
    date: 'Loading...',
    involved: 'Loading...',
    description: 'Loading...',
    judge: 'Loading...',
    form: 'Loading...',
    address: 'Loading...',
    courtName: 'Loading...'
  };

  var table = new Table;
  var animation = document.getElementById('loading');
  startAnimation();
  getByClassName(delBtn);
  loadAllCases();
  stopAnimation();


  // 'ADD NEW CASE' BUTTON:
  document.getElementById('add-form').onsubmit = function(e) {
    e.preventDefault();
    if (document.getElementById('add-input').value.match(/(.{3})\/(.*)\/(.{2})-[агкпц]/gi)){
      startAnimation();
      caseObject.number = document.getElementById('add-input').value;
      law.addNumber(caseObject.number);
      loadAllCases();
      stopAnimation();
    }
  };

  document.getElementById('add-button').onmousedown = function(e) {
    e.preventDefault();
    if (document.getElementById('add-input').value.match(/(.{3})\/(.*)\/(.{2})-[агкпц]/gi)){
      startAnimation();
    }
  };

  document.getElementById('add-button').onmouseout = function(e) {
    e.preventDefault();
    stopAnimation();
  };


  document.getElementById('add-input').onkeydown = function(event) {
    if ((event.keyCode == 13) && (document.getElementById('add-input').value.match(/(.{3})\/(.*)\/(.{2})-[агкпц]/gi)) ) {
        startAnimation();
    }
  };

  document.getElementById('add-input').onkeypress = function(event) {
    if ((event.keyCode == 13) ) {
      //code
    }
  };



  // GETTER FOR DELETE BUTTON
  function getByClassName(el) {
    for (i = 0; i < el.length; i++) {
      el[i].onclick = function() {
        if (this.parentNode.getAttribute('data-javaid')){
          law.deleteNumber(this.parentNode.getAttribute('data-javaid'));
        }
        this.parentNode.parentNode.remove();
        return false;
      };
    }
  }

  function loadAllCases(){
    law.getCases();
    // law.getCases();
    table.deleteAllRaws();
    for (let i = 0; i < law.getAmountOfCases(); i++) {
      law.fillTable(i);
    }
    document.getElementById('add-input').value = '';
    addResponsiveToTable();
    stopAnimation();
    return false;
  }


  function startAnimation(){
    // loading animation starts
    animation.style.display = 'inline';
  }

  function stopAnimation(){
    //loading animation stops
    animation.style.display = 'none';
  }

  // 'REFRESH' BUTTON:
  document.getElementById('refresh-btn').onclick = function(e) {
    e.preventDefault();
    loadAllCases();
    stopAnimation();
  };

  document.getElementById('refresh-btn').onmousedown = function(e) {
    e.preventDefault();
    startAnimation();
  };

  document.getElementById('refresh-btn').onmouseout = function(e) {
    e.preventDefault();
    stopAnimation();
  };
});
