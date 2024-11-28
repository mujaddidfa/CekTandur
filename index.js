const express = require('express');
<<<<<<< HEAD
const connectDB = require('./config/db');
=======
const connectDB = require('./db');
>>>>>>> cc5dfc060c087b052e478a1f1b98130b290d168c
const bodyParser = require('body-parser');
const authRoutes = require('./routes/authRoutes');
const userRoutes = require('./routes/userRoutes');
const plantRoutes = require('./routes/plantRoutes');
const solutionRoutes = require('./routes/solutionRoutes');

const app = express();
require('dotenv').config();
app.use(bodyParser.json());

app.use('/api/auth', authRoutes);
app.use('/api/users', userRoutes);

// app.use('/plant', plantRoutes);
// app.use('/solutions', solutionRoutes);

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
    console.log(`CekTandurAPI is running on port ${PORT}`);
});
