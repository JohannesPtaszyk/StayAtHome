//  
//  Constants.swift
//  StayAtHome-iOS
//
//  (c) Neofonie Mobile GmbH (2020)
//
//  This computer program is the sole property of Neofonie
//  Mobile GmbH (http://mobile.neofonie.de) and is protected
//  under the German Copyright Act (paragraph 69a UrhG).
//
//  All rights are reserved. Making copies, duplicating,
//  modifying, using or distributing this computer program
//  in any form, without prior written consent of Neofonie
//  Mobile GmbH, is prohibited.
//
//  Violation of copyright is punishable under the German
//  Copyright Act (paragraph 106 UrhG).
//
//  Removing this copyright statement is also a violation.

import Foundation


struct Constants {
    
    struct User {
        static let usernameKey = "User.Username"
        static let emailKey = "User.Email"
        static let scoreKey = "User.Score"
        static let imageKey = "User.Image"
        static let wifiName = "User.WifiName"
    }
    
    struct Storyboards {
        static let main = "Main"
        
        struct ViewController {
            static let MainViewController = "MainViewController"
            static let OnboardingViewController = "OnboardingViewController"
            
            struct Onboarding {
                static let start = "Start"
                static let userData = "UserDataViewController"
                static let locationPermission = "LocationPermissionViewController"
                static let acceptWIFI = "AcceptWIFIViewController"
            }
        }
        
    }
    
}
