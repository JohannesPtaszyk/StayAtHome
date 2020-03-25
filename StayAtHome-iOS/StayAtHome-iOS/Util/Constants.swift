import Foundation

struct Constants {
    
    struct Game {
        static let timerSpeed: Double = 10.0
    }
    
    struct User {
        static let uuidKey = "User.UUID"
        static let usernameKey = "User.Username"
        static let emailKey = "User.Email"
        static let scoreKey = "User.Score"
        static let imageKey = "User.Image"
        static let wifiName = "User.WifiName"
        static let level = "User.Level"
    }
    
    struct Storyboards {
        static let main = "Main"
        static let onboarding = "Onboarding"
        
        struct ViewController {
            static let MainViewController = "MainViewController"
            static let OnboardingViewController = "OnboardingViewController"
            static let leaderBoard = "LeaderBoardViewController"
            static let friends = "FriendsViewController"
            static let challenges = "ChallengesViewController"
            static let settings = "SettingsViewController"
            
            struct Onboarding {
                static let start = "Start"
                static let userData = "UserDataViewController"
                static let locationPermission = "LocationPermissionViewController"
                static let acceptWIFI = "AcceptWIFIViewController"
            }
        }
        
    }
    
}
