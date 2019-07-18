var data = [
    { name:"Телевизор", price:600, url:"https://www.avito.ru/irkutsk/audio_i_video/televizor_sssr_rabochiy_1444310875?slocation=621540"},
    { name:'Смартфон', price:7695, url:"https://www.mvideo.ru/products/smartfon-huawei-y9-2018-black-fla-lx1-30032651"},
    { name:'Соковыжималка', price:1790, url:"https://www.mvideo.ru/products/sokovyzhimalka-dlya-citrusovyh-moulinex-pc120870-20036960"},
    { name:'Наушники', price:690, url:"https://www.mvideo.ru/products/naushniki-vnutrikanalnye-jbl-c150-siu-black-jblc150siublk-50048263"},
    { name:'Клавиатура', price:590, url:"https://www.mvideo.ru/products/klaviatura-provodnaya-logitech-k120-for-business-920-002522-50048230"}
];

var data_entity = [];
var purch_name = [];
function generateTable(){

    var table = '<table class="simple-little-table">';
    var s;
    table += '<tr><th>Название товара</th><th>Цена</th><th>Ссылка</th><th>Action</th>';
    for( var i = 0; i < 5; i++ ){

        table += '<tr>';

        for( var j = 0; j < 4; j++ ){
            if(j === 0){
                //console.log(data[i].name);
                s = data[i].name;
            }
            else if(j === 1){
                s = data[i].price;
            }
            else if(j === 2){
                var url = data[i].url;
                s = '<a target="_blank" href='+url+' >ссылка на товар';
            }
            else if (j === 3){

                s = '<input id='+i+' class="green" type="button" value="Добавить" onclick="AddPurchase('+i+');"/>';
            }
            table += '<td>'+s+'</td>';
        }

        table += '</tr>';
    }
    table += '</table>';

    var bot_table = '<table class="simple-little-table">' +
        '<tr>' +
            '<th>Имя</th>' +
            '<th>Фамилия</th>' +
            '<th>Возраст</th>' +
            '<th>Содержимое покупки</th>'+
            '<th>Количество товара</th>' +
            '<th>Сумма покупки</th>'+
        '</tr>'+
            '<td><input class="bot" value="" id="NAME" type="text" size="30"></td>'+
            '<td><input class="bot" value="" id="LAST_NAME" type="text" size="30"></td>'+
            '<td><input class="bot" value="0" id="age" type="number" size="30"></td>'+
            '<td>' +
                '<div id="items"></div>' +
            '</td>'+
            '<td><input class="bot" id="count" type="number" size="30" readonly ></td>'+
            '<td><input class="bot" id="price" type="number" size="30" readonly ></td>'+
        '</table>';

    document.getElementById('table').innerHTML = table;
    document.getElementById('bot_table').innerHTML = bot_table;
}
var count = 0;
var price = 0.0;
var purchase_code;
function AddPurchase(index) {
    purchase_code = '';
    data_entity.push(data[index].url);
    purch_name.push(data[index].name);
    count++;
    price = price + data[index].price;
    var items = document.getElementById('items');
    purchase_code += '' +
        '<a target="_blank" id=index_row'+index+' href='+data[index].url+' >ссылка на товар</a>'+
        '<input class="green" id=index_but'+index+' type="submit" ' +
        'value="Удалить" class="green" readonly onclick="delPurchase('+index+')">' ;
    items.innerHTML += purchase_code;
    document.getElementById('count').value = count;
    document.getElementById('price').value = price;
}
function delPurchase(index) {
    count -=1;
    price = price - data[index].price;
    document.getElementById('count').value = count;
    document.getElementById('price').value = price;

    document.getElementById('index_but'+index+'').remove();
    document.getElementById('index_row'+index+'').remove();

    data_entity.splice(index,1);
    purch_name.splice(index,1);
    if(data_entity.length === 0){
        document.getElementById('items').innerHTML='';
    }
}

function setLocation() {

    var xhr = new XMLHttpRequest();
    var date = new Date(Date());
    var dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
        .toISOString()
        .split("T")[0];

    console.log(dateString);

    var xmlData = '<?xml version="1.0" encoding="UTF-8"?>';
    xmlData += '<item>';
    if(document.getElementById('NAME').value !=="" && document.getElementById('NAME').value !==null) {
        xmlData += '<name>' + document.getElementById('NAME').value + '</name>';
    }
    if(document.getElementById('LAST_NAME').value !== "" && document.getElementById('LAST_NAME').value !==null) {
        xmlData += '<lastname>' + document.getElementById('LAST_NAME').value + '</lastname>';
    }
    if(document.getElementById('age').value > 0 && document.getElementById('age').value !==null) {
        xmlData += '<age>' + document.getElementById('age').value + '</age>';
    }
    if(data_entity.length > -1) {
        xmlData += '<purchase_items>';
        for (var i1 = 0; i1 < data_entity.length; i1++) {
            xmlData += '<purchase_item>';
            xmlData += '<url>' + data_entity[i1] + '</url>';
            xmlData += '<name>' + purch_name[i1] + '</name>';
            xmlData += '</purchase_item>';
        }
        xmlData += '</purchase_items>';
    }

    xmlData += '<count>' + document.getElementById('count').value + '</count>';
    xmlData += '<amount>' + document.getElementById('price').value + '</amount>';
    xmlData += '<time>'+dateString+'</time>';
    xmlData += '</item>';


    xhr.onload = function () {
        if (xhr.status !== 200){
            if(xhr.status >=400 && xhr.status<500){
                alert('status: ' + xhr.status + ': Заполните все данные');
            }
        }
        else{
            alert('status: ' + xhr.status + ': Данные успешно внесены');
        }
    };

    xhr.open('POST', 'addxml', true);
    xhr.setRequestHeader('Content-type', 'text/xml');
    xhr.send(xmlData);
}
