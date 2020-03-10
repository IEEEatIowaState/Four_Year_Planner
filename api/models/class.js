//Models Serve to the DB and get from the DB
const ObjectId = require('mongodb').ObjectId;

const mongo = require('../helpers/mongoUtil.js');

const collectionName = 'class';

exports.add = (data, callback) => {
  //console.log(data);
  mongo.getDb().collection(collectionName).insertOne(data, function(err, res) {
    if (err) throw err;
  });
};