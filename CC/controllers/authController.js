const { db } = require('../config/db');
const { v4: uuidv4 } = require('uuid');
const bcrypt = require('bcryptjs');

exports.registerUser = async (req, res) => {
    const { name, email, password } = req.body;

    if (!name || !email || !password) {
        return res.status(400).json({
            status: 400,
            message: "Missing required fields",
            error: {
                details: "Please provide name, email, and password."
            }
        });
    }

    if (password.length < 8) {
        return res.status(400).json({
            status: 400,
            message: "Password too short",
            error: {
                details: "Password must be at least 8 characters long."
            }
        });
    }

    if (!email.includes('@')) {
        return res.status(400).json({
            status: 400,
            message: "Invalid email format",
            error: {
                details: "Email must contain '@' symbol."
            }
        });
    }

    const userQuerySnapshot = await db.collection('users').where('email', '==', email).get();
    if (!userQuerySnapshot.empty) {
        return res.status(409).json({  
            status: 409,
            message: "Account already registered",  
            error: {
                details: "The user has already registered with this email address."
            }
        });
    }

    const hashedPassword = await bcrypt.hash(password, 10); // 10 adalah jumlah salt rounds

    const userId = uuidv4().replace(/-/g, '').slice(0, 16);
    const insertedAt = new Date().toISOString();
    const updatedAt = insertedAt;

    try {
        const userRef = db.collection('users').doc(userId);
        const userData = { userId, name, email, password: hashedPassword, insertedAt, updatedAt };
        await userRef.set(userData);

        return res.status(201).json({
            status: 201,
            message: "User registered successfully",
            data: { userId, name, email, insertedAt, updatedAt }
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
        const userQuerySnapshot = await db.collection('users').where('email', '==', email).get();

        if (userQuerySnapshot.empty) {
            return res.status(400).json({
                status: 400,
                message: "Invalid credentials",
                error: {
                    details: "Authentication failed. User not found."
                }
            });
        }

        const userDoc = userQuerySnapshot.docs[0];
        const userData = userDoc.data();
        const isMatch = await bcrypt.compare(password, userData.password);

        if (!isMatch) {
            return res.status(400).json({
                status: 400,
                message: "Invalid credentials",
                error: {
                    details: "Authentication failed. Incorrect password."
                }
            });
        }

        return res.status(200).json({
            status: 200,
            message: "User logged in successfully",
            data: {
                userId: userDoc.id, 
                name: userData.name,
                email: userData.email
            }
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

exports.logoutUser = async (req, res) => {
    const { email } = req.body;

    try {
        const userQuerySnapshot = await db.collection('users').where('email', '==', email).get();

        if (userQuerySnapshot.empty) {
            return res.status(400).json({
                status: 400,
                message: "User not found",
                error: {
                    details: "Logout failed because the user does not exist."
                }
            });
        }

        const userDoc = userQuerySnapshot.docs[0];
        const userId = userDoc.id;
        const userData = userDoc.data();

        await db.collection('users').doc(userId).update({ isLoggedIn: false });

        return res.status(200).json({
            status: 200,
            message: "User logged out successfully",
            data: {
                userId: userData.id,
                name: userData.name,
                email: userData.email
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
