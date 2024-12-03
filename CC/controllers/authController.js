const { db } = require('../config/db');
const { v4: uuidv4 } = require('uuid');

exports.registerUser = async (req, res) => {
    const { name, email, password } = req.body;
    const id = uuidv4().replace(/-/g, '').slice(0, 16);
    const insertedAt = new Date().toISOString();
    const updatedAt = insertedAt;
    try {
        const userRef = db.collection('users').doc(id);
        const doc = await userRef.get();
        if (doc.exists) {
            return res.status(400).json({
                status: 400,
                message: "User already exists",
                error: {
                    details: "The user has registered an account with the same email address",
                }
            });
        }
        await userRef.set({ id, name, email, password, insertedAt, updatedAt });
        return res.status(201).json({
            status: 201,
            message: "User registered successfully",
            data: {
                userId: id,
                name: name,
                email: email
            }
        });
        
    } catch (error) {
        return res.status(500).json({
            status: 500,
            message: "Internal server error",
            error: {
                details: error.message
            }
        });
    }
};

exports.loginUser = async (req, res) => {
    const { email, password } = req.body;
    try {
        // Query untuk mencari dokumen berdasarkan email
        const userQuerySnapshot = await db.collection('users').where('email', '==', email).get();

        // Validasi jika user tidak ditemukan
        if (userQuerySnapshot.empty) {
            return res.status(400).json({
                status: 400,
                message: "Invalid credentials",
                error: {
                    details: "Authentication failed. User not found."
                }
            });
        }

        // Ambil data user dari dokumen pertama (jika ada)
        const userDoc = userQuerySnapshot.docs[0];
        const userData = userDoc.data();

        // Validasi password
        if (userData.password !== password) {
            return res.status(400).json({
                status: 400,
                message: "Invalid credentials",
                error: {
                    details: "Authentication failed. Incorrect password."
                }
            });
        }

        // Jika berhasil login
        return res.status(200).json({
            status: 200,
            message: "User logged in successfully",
    
        });

    } catch (error) {
        console.error("Error during login:", error);
        return res.status(500).json({
            status: 500,
            message: "Internal server error",
            error: {
                details: error.message
            }
        });
    }
};


// exports.loginUser = async (req, res) => {
//     const { email, password } = req.body;
//     try {
//         const userRef = db.collection('users').doc(email);
//         const doc = await userRef.get();
//         if (!doc.exists || doc.data().password !== password) {
//             return res.status(400).json({
//                 status: 400,
//                 message: "Invalid credentials",
//                 error: {
//                     details: "Authentication failed. Please check your username and password."
//                 }
//             });
//         }
//         return res.status(200).json({
//             status: 200,
//             message: "User logged in successfully",
//             data: doc.data()
//         });
//     } catch (error) {
//         return res.status(500).json({
//             status: 500,
//             message: "Internal server error",
//             error: {
//                 details: error.message
//             }
//         });
//     }
// };


// const { db } = require('../config/config');
// const hashPassword = require('../utils/hashPassword');

// const registerUser = async (req, res) => {
//     const { email, password } = req.body;

//     try {
//         const hashedPassword = await hashPassword(password);
//         await db.collection('users').doc(email).set({ email, password: hashedPassword });

//         res.status(201).json({ message: 'User registered successfully' });
//     } catch (error) {
//         res.status(500).json({ error: error.message });
//     }
// };

// const loginUser = async (req, res) => {
//     const { email, password } = req.body;

//     try {
//         const userDoc = await db.collection('users').doc(email).get();
//         if (!userDoc.exists) return res.status(404).json({ error: 'User not found' });

//         const isValidPassword = await hashPassword.compare(password, userDoc.data().password);
//         if (!isValidPassword) return res.status(401).json({ error: 'Invalid credentials' });

//         res.status(200).json({ message: 'Login successful' });
//     } catch (error) {
//         res.status(500).json({ error: error.message });
//     }
// };

// module.exports = { registerUser, loginUser };
