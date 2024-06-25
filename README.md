# MyPass


Description: My Pass is an Android app for easy bus pass management, built with Kotlin and Jetpack Compose. It allows users to apply for and manage bus passes. Using Firebase for authentication, real-time data, and cloud functions, My Pass ensures secure access and up-to-date pass information across devices. The app features a modern, user-friendly design for a seamless experience. 

Functionalities of the app :

1.User Registration: 
• Users register by providing their email address and creating a strong password.

• The system ensures that the password meets security requirements, including:

• Contains at least one uppercase letter, one lowercase letter, one digit, and one special 
character.

• Has a minimum character length of 8 characters to enhance password strength.

2. Login: 
• User can login using the provided credentials during the registration process.

• The Login form also consists of the validation and check if the user exists in database.

• The user will be navigated to the profile screen upon successful Login.

3. Password reset with email:

• Users are presented with a button for securely resetting their password using email sent 
to their registered email id.

• Implemented using firebase, this method provides user a safe way to reset their 
password while ensuring user is valid.

4. Sign-Out: Users can sign-out from the app using a button.

5. Pass Purchase: Users can seamlessly browse through a variety of pass options, each offering 
different combinations of pass types (such as ordinary and metro) and validity periods 
(including day pass, monthly, quarterly, etc.), each associated with distinct prices. Through an 
intuitive user interface built using Jetpack Compose, users can effortlessly explore these 
options, facilitated by interactive elements that allow for easy selection of desired passes. Upon 
selecting a pass, users can proceed to complete transactions securely, leveraging integrated 
payment processing functionalities. This ensures a smooth and secure experience, enabling 
users to acquire their preferred passes with confidence and convenience.

6. Pass Management: The application facilitates users with comprehensive pass management 
functionalities, enabling them to effortlessly view their passes. Users initiate their pass 
application process within the app, ensuring a streamlined experience. To maintain data 
integrity and prevent duplication, the system employs robust validation mechanisms, ensuring 
users cannot create duplicate passes. Once approved, users gain access to their active passes, 
presented within an intuitive interface. Passes are automatically deleted upon expiration, 
simplifying the pass management process.

7. Pass Validation: The system implements automatic validation mechanisms during the pass application process, ensuring that each pass is thoroughly verified before approval. This 
validation process occurs internally within the
app and is transparent to users. Once approved, passes are securely stored and are only 
accessible within the app. This seamless process ensures efficient and secure validation of 
passes, allowing for smooth transit experiences for passengers.

![IMG_20240617_014427](https://github.com/ALLURKARSUSHANTH/MyPass/assets/146927710/2de02377-03cb-4b8f-9901-2795db693ea3)
![IMG_20240617_014708](https://github.com/ALLURKARSUSHANTH/MyPass/assets/146927710/89b5b268-216b-4719-879d-773fc7d64829)
4427.jpg…]()
![IMG_20240617_014626](https://github.com/ALLURKARSUSHANTH/MyPass/assets/146927710/9d0f9653-2d6f-4dcf-84f5-cd1f32f49032)
ading IMG_20240617_01
