const express = require('express');
const bodyParser = require('body-parser');
const authRoutes = require('./routes/authRoutes');
const userRoutes = require('./routes/userRoutes');
const plantRoutes = require('./routes/plantRoutes');
const app = express();
require('dotenv').config();

app.use(bodyParser.json());
app.use('/api/auth', authRoutes);
app.use('/api/users', userRoutes);
app.use('/api/plant', plantRoutes);


const PORT = process.env.PORT || 8080;

app.listen(PORT, () => {
    console.log(`CekTandurAPI is running on port ${PORT}`);
});
