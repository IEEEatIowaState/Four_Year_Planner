//Routes handle all HTTP requests

const express = require('express');
const classModel = require('../models/class.js');

const router = express.Router();

//show an individual Class
router.get('/', (req, res) => {
    res.send('Hello World!');
  });

router.post('/class', (req, res) => {
  console.log(req.body)
  
  // req.query gives the query of the post request
  classModel.add(req.body, (err) => {
    if (err) return next(err);
    res.json({ success: true });
  });

  res.send(req.body);
});

module.exports = router;