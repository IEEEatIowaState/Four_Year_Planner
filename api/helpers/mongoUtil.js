const { MongoClient } = require('mongodb');

const DB_URL = 'mongodb://localhost:27017';
const DB_NAME = 'test';
let db;

module.exports = {

  //Connects to the mongo port: 27017
  connectToServer(callback) {
    MongoClient.connect(DB_URL, (err, client) => {
      if (err) {
        console.log(err);
        process.exit(1);
      }
      db = client.db(DB_NAME);
    });
  },

  //Method to get db as an object
  getDb() {
    return db;
  },
};