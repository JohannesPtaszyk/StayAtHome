//  
//  AppDelegate.swift
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

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

