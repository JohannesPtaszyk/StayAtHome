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
    
    func startGame(for user: User) {
        self.user = user
        
        timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { [weak self] (timer) in
            guard let strongSelf = self else { return }
            guard let user = strongSelf.user else { return }
            
            if strongSelf.shouldScorePoints() {
                user.score += 1
                strongSelf.delegate?.game(strongSelf, stateChanged: .connected)
            } else {
                strongSelf.delegate?.game(strongSelf, stateChanged: .disconnected)
            }
            
            
        }
    }
    
    func shouldScorePoints() -> Bool {
        hasWifi()
    }
    
    func hasWifi() -> Bool {
        return ManagerProvider.shared.wifiManager.getWiFiSsid() != nil
    }
    
}
