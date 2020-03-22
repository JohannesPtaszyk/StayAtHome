
import UIKit


class MainViewController: UIViewController, GameDelegate {

    @IBOutlet weak var userImageView: UIImageView!
    
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    
    var gameCoordinator: GameCoordinator?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        userNameLabel.text = gameCoordinator?.user?.username
        userImageView.image = gameCoordinator?.user?.image
        
        guard let user = gameCoordinator?.user else {
            return
        }
        
        gameCoordinator?.game.startGame(for: user)
        gameCoordinator?.game.delegate = self
        
        scoreLabel.text = String(user.score)
        
        view.backgroundColor = .gray
    }
    
    @IBAction func settingsButtonTapped(_ sender: Any) {
        
    }
    
    func game(_ game: Game, stateChanged state: GameState) {
        scoreLabel.text = String(game.score)
        
        view.backgroundColor = state == GameState.connected ? .green : .red
        
        gameCoordinator?.saveUser()
        
    }
    
}
