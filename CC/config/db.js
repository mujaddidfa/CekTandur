const admin = require("firebase-admin");
require('dotenv').config();

const serviceAccount = JSON.parse(
  Buffer.from(process.env.SERVICE_ACCOUNT_KEY, 'base64').toString('utf8')
);

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: process.env.URLDATABASE,
  storageBucket: process.env.GCP_STORAGE_BUCKET,  // Pastikan ini benar
});

const db = admin.firestore();
const bucket = admin.storage().bucket(); // Sekarang harus bisa mengakses bucket

module.exports = { admin, db, bucket };




// const admin = require("firebase-admin");
// require('dotenv').config();

// const serviceAccount = JSON.parse(
//   Buffer.from(process.env.SERVICE_ACCOUNT_KEY, 'base64').toString('utf8')
// );

// admin.initializeApp({
//   credential: admin.credential.cert(serviceAccount),
//   databaseURL: process.env.DATABASE_URL,
//   storageBucket: process.env.GCP_STORAGE_BUCKET, 
// });

// const db = admin.firestore();
// const bucket = admin.storage().bucket();

// module.exports = { admin, db, bucket };

/* const admin = require("firebase-admin");
const serviceAccount = require("../ServiceAccount_Firebase.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: process.env.URLDATABASE,
});

const db = admin.firestore();
module.exports = { admin, db };
 */

