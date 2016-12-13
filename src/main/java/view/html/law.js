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
