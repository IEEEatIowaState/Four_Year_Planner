const express = require('express');
const router = express.Router();
router.use('/', require('./class'));
router.use('/class', require('./class'));
module.exports = router;