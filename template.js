/* Written for a local tech startup (that shall remain anonymous)
   Replaces every occurence of "%%END%%" with the current date and every occurence of "%%START%%" with the date two days ago
*/

window.onload = main;

function main() {
    var links = document.querySelectorAll("a");
    var today = new Date();
    var y = today.getFullYear();
    var m = today.getMonth(); //0-11  +1
    var d = today.getDate();
    var end = format(y, m + 1, d);
    var start = new Date(y, m, d - 2);
    var startFormatted = format(start.getFullYear(), start.getMonth() + 1, start.getDate());
    links.forEach(function(link) {
        var prev = link.getAttribute("href");
        var v1 = prev.replace(/%%END%%/g, end);
        var v2 = v1.replace(/%%START%%/g, startFormatted);
        link.setAttribute("href", v2);
        // console.log(v2);
    });

    function format(y, m, d) {
        var res = y + '-';
        if (m < 10) res += '0';
        res += m + '-';
        if (d < 10) res += '0';
        res += d;
        return res;
    };
};
