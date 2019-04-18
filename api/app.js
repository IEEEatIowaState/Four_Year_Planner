// For info on file sturcture and basics of Express/node https://www.terlici.com/2014/08/25/best-practices-express-structure.html
const express = require('express');
const app = express();
const mongo = require('./helpers/mongoUtil.js');
var db;

//app.use(express.static(__dirname + '/public'));
app.use(require('./routes/api'));

/*
mongo.connect('mongodb://localhost:27017', (err, client) => {
    if (err) {
      console.error(err)
      return
    }
    db = client.db('test');
    db.collection("class").insertOne({Class: "CprE 281"}, function(err, res) {
      if (err) throw err;
      console.log("1 document inserted");
    });
  })
*/
app.listen(3000, function() {
  console.log('Listening on port 3000...')
  mongo.connectToServer();
})

