document.addEventListener('DOMContentLoaded', function () {
  addResponsiveToTable();
});


function addResponsiveToTable() {
  var headertext = [],
  headers = document.querySelectorAll("#law th"),
  tablerows = document.querySelectorAll("#law th"),
  tablebody = document.querySelector("#law tbody");

  for(var i = 0; i < headers.length; i++) {
    var current = headers[i];
    headertext.push(current.textContent.replace(/\r?\n|\r/,""));
  }
  for (var i = 0, row; row = tablebody.rows[i]; i++) {
    for (var j = 0, col; col = row.cells[j]; j++) {
      col.setAttribute("data-th", headertext[j]);
    }
  }
}
