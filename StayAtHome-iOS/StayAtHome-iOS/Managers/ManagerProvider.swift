import UIKit
import CoreLocation


enum ReceiveSSIDError {
    case general
    case noLocationService
    case noWifi
    
    var headerText: String {
        switch self {
        case .general: return "Uppsii!"
        case .noLocationService: return "Location Service fehlt."
        case .noWifi: return "WiFi nicht Erkannt!"
        }
    }
    
    var descriptionText: String {
        switch self {
        case .general: return "Irgendetwas ist schiefgelaufen. Versuche es gleich noch einmal."
        case .noLocationService: return "Der Location Service ist Pflicht!"
        case .noWifi: return "Es scheint so als bist du nicht im WiFi. Das ist aber Pflicht!"
        }
    }
}

enum Level: Int {
    case dayfly = 0
    
    var title: String {
        switch self {
        case .dayfly:
            return "Eintagsfliege".uppercased()
        }
    }
}

class User {
    var username: String
    var email: String
    var score: Int
    var image: UIImage?
    var wifiName: String?
    var level: Level = .dayfly
    
    init(username: String, email: String, score: Int) {
        self.username = username
        self.email = email
        self.score = score
    }
}

class UserDataProvider {
    
    enum FetchUserError: Error {
        case noUserFound
    }
    
    class func fetchUser(_ completion: (Result<User, FetchUserError>) -> Void) {
        
        let score = UserDefaults.standard.integer(forKey: Constants.User.scoreKey)
        
        if let username = UserDefaults.standard.string(forKey: Constants.User.usernameKey),
            let email = UserDefaults.standard.string(forKey: Constants.User.emailKey) {
            
            let user = User(username: username, email: email, score: score)
            
            user.wifiName = UserDefaults.standard.string(forKey: Constants.User.wifiName)
            
            if let data = UserDefaults.standard.data(forKey: Constants.User.imageKey) {
                user.image = UIImage(data: data)
            }
            
            user.level = Level(rawValue: UserDefaults.standard.integer(forKey: Constants.User.level)) ?? .dayfly
            
            completion(.success(user))
            return
        }
        
        completion(.failure(.noUserFound))
        
    }
    
    class func save(user: User) {
        UserDefaults.standard.set(user.score, forKey: Constants.User.scoreKey)
        UserDefaults.standard.set(user.username, forKey: Constants.User.usernameKey)
        UserDefaults.standard.set(user.email, forKey: Constants.User.emailKey)
        UserDefaults.standard.set(user.image?.pngData(), forKey: Constants.User.imageKey)
        UserDefaults.standard.set(user.wifiName, forKey: Constants.User.wifiName)
        UserDefaults.standard.set(user.level.rawValue, forKey: Constants.User.level)
    }
    
}

class ManagerProvider {

    static let shared = ManagerProvider()
    
    var locationManager = LocationManager()
    var wifiManager = WifiManager()
    
    
}

