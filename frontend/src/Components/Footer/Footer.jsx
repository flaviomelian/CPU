import github from '../../assets/logo-github.jpg'
import linkedin from '../../assets/Linkedin.png'
import portfolio from '../../assets/dev.png'
import './Footer.css'

const Footer = () => {
  return (
    <footer>
      <p>flaviomelian &copy; - 2025</p>
      <div>
        <a href="" target="_blank" rel="noreferrer">
          <img src={github} alt="GitHub" />
        </a>
        <a href="" target="_blank" rel="noreferrer">
          <img src={linkedin} alt="LinkedIn" />
        </a>
        <a href="" target="_blank" rel="noreferrer">
          <img src={portfolio} alt="Portfolio" />
        </a>
      </div>
    </footer>
  )
}

export default Footer
