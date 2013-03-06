/*
 * OptionScene.cpp
 *
 *  Created on: Mar 1, 2013
 *      Author: john
 */

#include "/home/john/android/cocos2d-2.0-x-2.0.4/project3/Classes/OptionScene.h"
#include "SimpleAudioEngine.h"
#include "HomeScene.h"
#include "GameScene.h"

using namespace cocos2d;
using namespace CocosDenshion;

OptionScene::OptionScene()
{
	soundMenu = NULL;
	soundItem = NULL;
}

OptionScene::~OptionScene()
{
	// TODO Auto-generated destructor stub
}

bool OptionScene::init()
{
	if (!CCLayer::init()) return false;


	//----------------------------------------------------------------------------------------------------------------------------------------------------
	//--add sprites: title, sound, and return
	//----------------------------------------------------------------------------------------------------------------------------------------------------

	//get window size
	CCSize winSize = CCDirector::sharedDirector()->getWinSize();

	//get sound state
	soundOn = CCUserDefault::sharedUserDefault()->getBoolForKey("soundOn");

	//create title, set scale
	CCSprite *option = CCSprite::create("options.png");
	option->setScaleX(winSize.width/(option->getContentSize().width*1.5));
	option->setScaleY(winSize.height/(option->getContentSize().height*3));
	option->setPosition(ccp(winSize.width/2, winSize.height-winSize.height/4));
	this->addChild(option);

	//create title, set scale
	CCSprite *sound = NULL;
	if(soundOn) sound = CCSprite::create("on.png");
	else sound = CCSprite::create("off.png");
	sound->setScaleX(winSize.width/(2.5*sound->getContentSize().width));
	sound->setScaleY(winSize.height/(5.5*sound->getContentSize().height));

	//create title, set scale
	CCSprite *ret = CCSprite::create("return.png");
	ret->setScaleX(winSize.width/(4*ret->getContentSize().width));
	ret->setScaleY(winSize.height/(8*ret->getContentSize().height));


	//----------------------------------------------------------------------------------------------------------------------------------------------------
	//--add menus & items
	//----------------------------------------------------------------------------------------------------------------------------------------------------

	//create the two items
	soundItem = CCMenuItemSprite::create(sound, sound, this, menu_selector(OptionScene::menuSoundCallback));
	if(!soundItem) return false;

	//create menu
	soundMenu = CCMenu::create(soundItem, NULL);
	if(!soundMenu) return false;

	//setup positioning
	soundMenu->setPosition(ccp(21*winSize.width/32, winSize.height/2));

	//add menu item as layer
	this->addChild(soundMenu, 1);

	//create the two items
	CCMenuItemSprite *pReturnItem = CCMenuItemSprite::create(ret, ret, this, menu_selector(OptionScene::menuReturnCallback));
	if(!ret) return false;

	//create menu
	CCMenu* pMenu2 = CCMenu::create(pReturnItem, NULL);
	if(!pMenu2) return false;

	//setup positioning
	pMenu2->setPosition(ccp(3*winSize.width/4, winSize.height/4));

	//add menu item as layer
	this->addChild(pMenu2, 1);

	return true;
}

CCScene* OptionScene::scene()
{
    // 'scene' is an autorelease object
    CCScene *scene = CCScene::create();

    // 'layer' is an autorelease object
    OptionScene *layer = OptionScene::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}


void OptionScene::menuSoundCallback(CCObject* pSender)
{
	//get window size
	CCSize winSize = CCDirector::sharedDirector()->getWinSize();

	soundMenu->removeChild(soundItem, true);

	//create title, set scale
	CCSprite *sound = NULL;
	if(soundOn)
	{
		sound = CCSprite::create("off.png");
		CCUserDefault::sharedUserDefault()->setBoolForKey("soundOn", false);
	}
	else
	{
		sound = CCSprite::create("on.png");
		CCUserDefault::sharedUserDefault()->setBoolForKey("soundOn", true);
	}

	sound->setScaleX(winSize.width/(2.5*sound->getContentSize().width));
	sound->setScaleY(winSize.height/(5.5*sound->getContentSize().height));

	//create the two items
	soundItem = CCMenuItemSprite::create(sound, sound, this, menu_selector(OptionScene::menuSoundCallback));
	if(!soundItem) return;

	//create menu
	soundMenu = CCMenu::create(soundItem, NULL);
	if(!soundMenu) return;

	//setup positioning
	soundMenu->setPosition(ccp(21*winSize.width/32, winSize.height/2));

	//add menu item as layer
	this->addChild(soundMenu, 1);

	soundOn = CCUserDefault::sharedUserDefault()->getBoolForKey("soundOn");
}

void OptionScene::menuReturnCallback(CCObject* pSender)
{
    // create a scene. it's an autorelease object
    CCScene *pScene = HomeScene::scene();

    // run
    CCDirector::sharedDirector()->replaceScene(pScene);
}
