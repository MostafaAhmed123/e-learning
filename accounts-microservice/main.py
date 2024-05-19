from flask import Flask, request, jsonify, abort
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine
from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm.exc import NoResultFound
from sqlalchemy.exc import OperationalError
from werkzeug.security import check_password_hash, generate_password_hash
import re
from flask_cors import CORS

app = Flask(__name__)
app.config["SQLALCHEMY_DATABASE_URI"] = "mysql://root:mostafa@localhost:3306/users"
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
db = SQLAlchemy(app)
CORS(app, resources={r"/*": {"origins": "*"}})


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)
    password = db.Column(db.String(255), nullable=False)
    role = db.Column(db.String(50), nullable=False)
    affiliation = db.Column(db.String(100))
    years_of_experience = db.Column(db.Integer)
    bio = db.Column(db.Text)

    def set_password(self, password):
        self.password = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.password, password)

    def __repr__(self):
        return f"<User(id={self.id}, name='{self.name}', email='{self.email}', role='{self.role}', affiliation='{self.affiliation}', years_of_experience={self.years_of_experience}, bio='{self.bio}')>"


EMAIL_REGEX_PATTERN = r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"


@app.route("/users/login", methods=["POST"])
def login():
    data = request.json
    if not data or not all(key in data for key in ["email", "password"]):
        abort(400, "Missing email or password")

    email = data["email"]
    password = data["password"]

    try:
        user = User.query.filter_by(email=email).one()
    except NoResultFound:
        abort(401, "Invalid email or password")

    if not user.check_password(password):
        abort(401, "Invalid email or password")

    user_data = {
        "id": user.id,
        "name": user.name,
        "email": user.email,
        "role": user.role,
        "affiliation": user.affiliation,
        "years_of_experience": user.years_of_experience,
        "bio": user.bio,
    }
    return jsonify(user_data)


@app.route("/users/register", methods=["POST"])
def register_user():
    data = request.json
    if not data or not all(
        key in data for key in ["name", "email", "password", "role"]
    ):
        abort(400, "Missing required data")

    if not re.match(EMAIL_REGEX_PATTERN, data["email"]):
        abort(400, "Invalid email format")
    if data["role"] == "student":
        user = User(
            name=data["name"],
            email=data["email"],
            role="student",
            affiliation=data.get("affiliation"),
            bio=data.get("bio"),
        )
        user.set_password(data["password"])

        db.session.add(user)
    else:
        user = User(
            name=data["name"],
            email=data["email"],
            role="instructor",
            affiliation=data.get("affiliation"),
            years_of_experience=data.get("years_of_experience"),
            bio=data.get("bio"),
        )
        user.set_password(data["password"])

        db.session.add(user)
    try:
        db.session.commit()
    except IntegrityError:
        db.session.rollback()
        abort(400, "Email already exists")

    return jsonify({"message": "User registered successfully"}), 201


@app.route("/user", methods=["GET", "PUT", "DELETE"])
def manage_user():
    try:
        user_id = id = request.args.get("id")
        user = User.query.filter_by(id=user_id).one()
    except NoResultFound:
        abort(404, "User not found")

    if request.method == "GET":
        return jsonify(
            {
                "id": user.id,
                "name": user.name,
                "email": user.email,
                "role": user.role,
                "affiliation": user.affiliation,
                "years_of_experience": user.years_of_experience,
                "bio": user.bio,
            }
        )

    elif request.method == "PUT":
        data = request.json
        if not data:
            abort(400, "No data provided")

        if "name" in data:
            user.name = data["name"]
        if "email" in data:
            if not re.match(EMAIL_REGEX_PATTERN, data["email"]):
                abort(400, "Invalid email format")
            user.email = data["email"]
        if "password" in data:
            user.set_password(data["password"])
        if "role" in data:
            user.role = data["role"]
        if "affiliation" in data:
            user.affiliation = data["affiliation"]
        if "years_of_experience" in data and user.role == "instructor":
            user.years_of_experience = data["years_of_experience"]
        if "bio" in data:
            user.bio = data["bio"]

        db.session.commit()
        return jsonify({"message": "User updated successfully"})

    elif request.method == "DELETE":
        db.session.delete(user)
        db.session.commit()
        return jsonify({"message": "User deleted successfully"})

    else:
        abort(405, "Method not allowed")


@app.route("/users", methods=["GET"])
def get_all_users():
    users = User.query.all()
    user_list = []
    for user in users:
        user_data = {
            "id": user.id,
            "name": user.name,
            "email": user.email,
            "role": user.role,
            "affiliation": user.affiliation,
            "years_of_experience": user.years_of_experience,
            "bio": user.bio,
        }
        user_list.append(user_data)

    return jsonify(user_list)


@app.route("/usertype", methods=["GET"])
def get_user_type():
    id = request.args.get("id")
    if not id:
        abort(400, "id parameter is required")

    try:
        user = User.query.filter_by(id=id).one()
        return jsonify({"role": user.role})
    except NoResultFound:
        return jsonify({"role": "none"})


def init_fun():
    try:
        admin_user = User.query.filter_by(email="admin@ecom.com").first()
        if admin_user:
            print("Admin user already exists. Initialization skipped.")
            return
        admin_user = User(
            name="Admin",
            email="admin@ecom.com",
            role="admin",
            password="admin",
        )
        admin_user.set_password(admin_user.password)
        db.session.add(admin_user)
        db.session.commit()
        print("Admin user created successfully.")
    except Exception as e:
        print("Error initializing admin user:", e)


if __name__ == "__main__":
    with app.app_context():
        db.create_all()
        init_fun()
    app.run(debug=False)
