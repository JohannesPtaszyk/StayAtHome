import Foundation

protocol GameDelegate {
    func game(_ game: Game, stateChanged state: GameState)
}

enum GameState {
    case connected
    case disconnected
}


class Game {
    
    var timer: Timer?
    
    var user: User?
    var delegate: GameDelegate?
    
    var score: Int {
        user?.score ?? 0
    }
    
    private var gameOperationQueue = OperationQueue()
    
    func startGame(for user: User) {
        
        self.user = user
        
        gameOperationQueue.addOperation { [weak self] in
            
            while true {
                
                guard let strongSelf = self else { return }
                guard let user = strongSelf.user else { return }
                
                if strongSelf.shouldScorePoints() {
                    user.score += 1
                    DispatchQueue.main.async {
                        strongSelf.delegate?.game(strongSelf, stateChanged: .connected)
                    }
                } else {
                    DispatchQueue.main.async {
                        strongSelf.delegate?.game(strongSelf, stateChanged: .disconnected)
                    }
                }
                
                sleep(UInt32(Constants.Game.timerSpeed))
            }
            
        }
        
    }
    
    func shouldScorePoints() -> Bool {
        hasWifi()
    }
    
    func hasWifi() -> Bool {
        return ManagerProvider.shared.wifiManager.getWiFiSsid() != nil
    }
    
    func generateRandomCheer() -> String {
        [
            "WoooooooW",
            "Weiter soooooo!",
            "Du hast es echt drauf, diggi!",
            "Alter falter wie gehst du den ab?!",
            "Schau dir den an.",
            "Hast du dafür eine Ausbildung gemacht?"
        ].randomElement() ?? "WoooooW"
    }
    
}
