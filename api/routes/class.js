//Routes handle all HTTP requests

const express = require('express');
const classModel = require('../models/class.js');

const router = express.Router();

//show an individual Class
router.get('/', (req, res) => {
    res.send('Hello World!');
  });

router.post('/class', (req, res) => {

  // req.query gives the body of the post request
  classModel.add(req.query, (err) => {
    if (err) return next(err);
    res.json({ success: true });
  });

  res.send(req.query);
});

module.exports = router;