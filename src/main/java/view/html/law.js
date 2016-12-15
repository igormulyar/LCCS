document.addEventListener('DOMContentLoaded', function () {

    var Law = function() {
    var LINK = 'localhost:8080/';


    this.addNumber = function() {
      // POST
      // body:{number}"	/tracked_numbers/
      // Добавить номер дела для отслеживания
      // Этот запрос на добавление переданного пользователем номера дела к номерам для отслеживания.
      // Делается POST запрос к ресурсу tracked_numbers, тело запроса должно содержать номер дела, который мы добавляем - ""number""
    }

    this.deleteNumber = function() {
      // DELETE
      // /tracked_numbers/number
      // Удалить номер дела, которое нужно отслеживать
      // в url- е укзывается номер дела, которое мы больше не хотим отвлеживать (number)
    }

    this.showList = function() {
      //  GET
      // /tracked_numbers/
      // Показать список номеров дел, которые отслеживаются
      // response возвращает массив номеров дел [{number}{number}{number}] в джейсоне,
      // чтобы показывать пользователю, какие номера сейчас are supposed to be tracked"
    }


    this.showInfo = function() {
      //  GET
      // received_cases
      // показать информацию по отслеженным (найденным) делам
      // response возвращает массив дел (а каждое дело содержит поля: дата, номер, учасники и т.п.) в джейсоне.
      // Информация просто достается из базы данных без выполнения предварительного поиска, база не обновляется.
      // Это та главная нформация, которая интересует пользователя.
    }

    this.refreshInfo = function() {
      // PUT????
      // received_cases
      // обновить информацию по отслеженным делам (выполнить новый поиск)
      // response возвращает массив дел (а каждое дело содержит поля: дата, номер, учасники и т.п.) в джейсоне.
      // Прежде чем вернуть массив дел, будет проведен новый поиск, результаты которого будут записаны в базу данных.
    }


  };

  var law = new Law;
  law.addNumber();


// WORK WITH TABLE

var delBtn = document.getElementsByClassName('delete-btn');


  var Table = function() {
    var TABLE_ID = 'tbody';

    this.addRow = function(obj) {
      obj = obj || {number: 'empty'};

      var tr = document.createElement('tr');
      tr.innerHTML = '<td><span class="delete-btn"><i class="fa fa-trash-o" aria-hidden="true"></i></span> ' + obj.number + '</td>' +
                      '<td>' + obj.date + '</td>' +
                      '<td>' + obj.involved + '</td>' +
                      '<td>' + obj.desription + '</td>' +
                      '<td>' + obj.judge + '</td>' +
                      '<td>' + obj.form + '</td>' +
                      '<td>' + obj.address + '</td>' ;
      document.getElementById(TABLE_ID).appendChild(tr);
      getByClassName(delBtn);
      return false;
    }

    this.deleteRow = function(number) {
    }

  };

  temp = {
    number: '0',
    date: 'Loading...',
    involved: 'Loading...',
    desription: 'Loading...',
    judge: 'Loading...',
    form: 'Loading...',
    address: 'Loading...'
  };

  var table = new Table;
  getByClassName(delBtn);




  document.getElementById('add-form').onsubmit = function() {
    temp.number = document.getElementById('add-input').value;
    table.addRow(temp);
    addResponsiveToTable();
    return false;
  };


  function getByClassName(el) {
    for (i = 0; i < el.length; i++) {
      el[i].onclick = function() {
        this.parentNode.parentNode.remove();
        return false;
      };
    }
  }



});
