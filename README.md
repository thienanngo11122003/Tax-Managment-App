# Tax Management App  

## Overview  
The **Tax Management App** is an Android application designed to simplify the tax filing process for both customers and administrators. The app offers an intuitive interface for users to register, log in, and manage their tax-related tasks. Admins can oversee customer progress, update statuses, and manage records efficiently.  

## Features  
### Customer Features  
- **Registration & Login**: Secure Firebase Authentication for user management.  
- **Profile Management**: View and update personal details, including name, address, and contact information.  
- **Tax Filing Status**: Monitor the progress of your tax filing process with visually coded status indicators.  

### Admin Features  
- **Customer Management**: Access and manage customer details with an option to update their process statuses.  
- **Swipe-to-Delete**: Remove customer records with a swipe gesture.  
- **Status Management**: Assign and modify customer statuses, such as:  
  - **AWAITED**: Yellow  
  - **FAILEDTOREACH**: Light Red  
  - **ONBOARDED**: Light Green  
  - **INPROCESS**: Mid Green  
  - **COMPLETED**: Dark Green  
  - **DENIED**: Red  

## Tech Stack  
- **Language**: Java  
- **Database**: RoomDB for local data persistence  
- **Geocoding**: Google Maps API for address-to-coordinates conversion  
- **Authentication**: Firebase Authentication for secure user access  
- **UI/UX**: Material Design principles for a clean and intuitive interface  

## Demo  
> Provide screenshots or a short video showcasing the app’s key functionalities.  

## Setup Instructions  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/thienanngo11122003/Tax-Managment-App.git  
