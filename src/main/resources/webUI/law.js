document.addEventListener('DOMContentLoaded', function () {

    var EMPTY = 'no info';

    var Law = function() {
    var LINK = 'http://localhost:8080',
        casesIdArray,
        numbersArray,
        allInfoArray;

    this.getcasesIdArray = function(){
      return casesIdArray;
    }

    this.getAllInfoArray = function(){
      return allInfoArray;
    }

    this.getAmountOfCases = function(){
      return casesIdArray.length;
    }

    this.addNumber = function(number) {
      // POST body:{number}	/tracked_numbers/
      // Добавить номер дела для отслеживания
      // Этот запрос на добавление переданного пользователем номера дела к номерам для отслеживания.
      // Делается POST запрос к ресурсу tracked_numbers, тело запроса должно содержать номер дела, который мы добавляем - ""number""
      var xhr = new XMLHttpRequest(),
          url = LINK + "/tracked_numbers/",
          params = number;
      xhr.open("POST", url, true);
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

    this.showList = function() {
      //  GET /tracked_numbers/
      // Показать список номеров дел, которые отслеживаются
      // response возвращает массив номеров дел [{number}{number}{number}] в джейсоне,
      // чтобы показывать пользователю, какие номера сейчас are supposed to be tracked"
      var xhr = new XMLHttpRequest(),
          url = LINK + "/tracked_numbers/",
          params = '',
          result = 'empty';
          casesIdArray = [];
          numbersArray = [];
      xhr.open("GET", url, false);
      xhr.send(params);
      for (i = 0; i < JSON.parse(xhr.responseText).length; i++ ){
        casesIdArray.push(JSON.parse(xhr.responseText)[i].id);
        numbersArray.push(JSON.parse(xhr.responseText)[i].number);
      }
      return xhr.responseText;
    }


    this.getIdByName = function (name) {
      var result;
      for (i = 0; i < JSON.parse(this.showList()).length; i++ ) {
        if (name == JSON.parse(this.showList())[i].number) {
          result =  JSON.parse(this.showList())[i].id;
        }
      }
      return result;
    }


    this.showInfo = function() {
      //  GET /hearings
      // обновить и показать информацию по отслеженным делам (выполнить новый поиск)
      // response возвращает массив дел (а каждое дело содержит поля: дата, номер, учасники и т.п.) в джейсоне.
      // Прежде чем вернуть массив дел, будет проведен новый поиск, результаты которого будут записаны в базу данных.
      allInfoArray = [];
      var xhr = new XMLHttpRequest(),
          url = LINK + "/hearings/",
          params = '';
      xhr.open("GET", url, false);
      xhr.send(params);
      allInfoArray = JSON.parse(xhr.responseText);
      return xhr.responseText;
    }

    this.fillTable = function(num) {

      if (!allInfoArray[num]){
        allInfoArray[num] = {date: EMPTY,
                            involved: EMPTY,
                            description: EMPTY,
                            judge: EMPTY,
                            forma: EMPTY,
                            add_address: EMPTY};
      }
      var buffer = {
          number: numbersArray[num],
          id: casesIdArray[num],
          date: allInfoArray[num].date || 'noinfo',
          involved: allInfoArray[num].involved,
          description: allInfoArray[num].description,
          judge: allInfoArray[num].judge,
          forma: allInfoArray[num].forma,
          add_address: allInfoArray[num].add_address
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
      var obj = obj || {number: 'n', date: '01.01.1999 10:00', involved: 'i', description: 'd', judge: 'j', forma: 'f', add_address: 'a' };
      var result = '#';

      result = 'https://calendar.google.com/calendar/render?action=TEMPLATE' +
               '&text=' + encodeURIComponent(obj.number) +
               '&location=' + encodeURIComponent(obj.add_address) +
               '&dates=' + encodeURIComponent(this.convertDateToGoogleFormat(obj.date)) +
               '&details=' + encodeURIComponent(obj.forma + ': ' +  obj.description + '.\n' + obj.involved + '.\nСуддя: ' + obj.judge ) +
               '&trp=false&pli=1&sf=true&output=xml';

      return (result);
    }

    this.addRow = function(obj) {
      obj = obj || {number: 'empty'};
      var tr = document.createElement('tr');  //law.getIdByName(obj.id)
      // tr.className = this.getTimeMarker(obj.date);
      tr.innerHTML = '<td data-javaid="' + obj.id + '"><span class="delete-btn"><i class="fa fa-trash-o" aria-hidden="true"></i></span> ' + obj.number + '</td>' +
                      '<td><a href="' + this.createGoogleLink(obj) + '" title="Add to Google calendar" class="google-link" target="_blank"><i class="fa indicator ' +
                      this.getTimeMarker(obj.date) + '" aria-hidden="true"></i></a> ' + this.breakDate(obj.date) + '</td>' +
                      '<td>' + obj.involved + '</td>' +
                      '<td>' + obj.description + '</td>' +
                      '<td>' + obj.judge + '</td>' +
                      '<td>' + obj.forma + '</td>' +
                      '<td>' + obj.add_address + '</td>' ;
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
    forma: 'Loading...',
    add_address: 'Loading...'
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
    law.showList();
    law.showList();
    law.showInfo();
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
