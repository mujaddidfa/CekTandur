const admin = require('firebase-admin');
const serviceAccount = require('./ServiceAccountKey.json'); // Firebase private key

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://backend-capstone.firebaseio.com"
});

const db = admin.firestore();

module.exports = { admin, db };
